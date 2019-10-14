var votes = [];
// var gamesDom;

var gameRenderXslDom;
var gameDetailRenderXslDom;
const Constant = {
    TREND_GAME: "BoardgameRecommendation/webresources/games/trend",
    VOTED_GAME: "BoardgameRecommendation/webresources/games/voted",
    SUGGESTED_GAME: "BoardgameRecommendation/webresources/games/suggested"
};
var offsetClient = 0;
var fetchClient = 20;
var offsetServer = 0;
var fetchServer = 100;
var distance = 200; // 100px

function initRatePoint(obj) {
    var point = obj.getAttribute("value") * 2;
    var resultRadio = obj.querySelector('input[value="' + point + '"]');
    resultRadio.checked = true;
}



function onLoad() {
    //init vote array
    var voteString = localStorage.getItem("votes");
    votes = JSON.parse(voteString);
    if (votes == null) {
        votes = [];
    }

    //init stylesheet
    gameRenderXslDom = Utils.parseToXmlDom(gameRenderXsl);
    gameDetailRenderXslDom = Utils.parseToXmlDom(gameDetailRenderXsl);

    //init games
    // gamesDom = Utils.parseToXmlDom(gamesString);
    loadGame(offsetClient, fetchClient);

    //init rate point
    var rateFieldSet = document.querySelectorAll("fieldset[value]");
    for (var i = 0; i < rateFieldSet.length; i++) {
        initRatePoint(rateFieldSet[i]);
    }

    //init search
    var searchForm = document.getElementById("search-form");
    if (searchForm != null) {
        searchForm.addEventListener("submit", onFormSubmit);
    }

    //init scroll
    var element = document;
    element.addEventListener("scroll", function() {
        onScroll(element, distance);
    });

}

function initGameDetail(element) {

    var gameId = element.getAttribute("value");
    //call server
    var url = baseUrl + "?id=" + gameId;
    Utils.callToServer(url, "GET", null, true, function(responseXML) {
        if (responseXML) {
            displayGameDetail(responseXML);
        }
    });
}

function displayGameDetail(gameDetailXmlDom) {
    // var count = 1;
    var isDisplay = true;
    var gameDetailDiv = document.getElementById("game-detail");
    //check div tồn tại thì xóa
    if (gameDetailDiv) {
        document.body.removeChild(gameDetailDiv);
    }

    var header = document.getElementsByTagName("header")[0];
    var gameSection = document.getElementsByClassName("game-section")[0];

    var resultDom = new Document();
    var resultDom = Utils.applyXsl(gameDetailXmlDom, gameDetailRenderXslDom, resultDom);
    console.log(resultDom);

    document.body.insertBefore(resultDom, document.body.firstChild);

    gameDetailDiv = document.getElementById("game-detail");
    gameDetailDiv.style.display = "block";

    header.style.filter = "blur(10px) brightness(30%)";
    gameSection.style.filter = "blur(10px) brightness(30%)";

    document.onclick = function(e) {
        if (!gameDetailDiv.contains(e.target) && isDisplay) {
            gameDetailDiv.style.display = "none";
            header.style.filter = "none";
            gameSection.style.filter = "none";
            isDisplay = false;
        }
        // count++;
    }

}

function onFormSubmit(e) {
    e.preventDefault();
    var searchValue = document.getElementById("search-input").value.trim();
    if (searchValue && searchValue.length != 0) {
        var result = search(searchValue);
        if (!result) {
            var confirm = window.confirm("Click ok to perform advanced search");
            if (confirm) {
                document.getElementById("search-form").submit();
            }
            return;
        }
        var gameList = document.getElementsByClassName("game-list")[0];
        gameList.replaceWith(result);
    }
}

function onBeforeUnload() {
    if (votes != null && votes.length > 0) {
        var url = "vote";
        var param = "xml=" + Utils.convertArrayToXML(votes, "votes");
        Utils.callToServer(url, "POST", param, false, function() {
            localStorage.removeItem("votes");
        });
    }
}


function onVote(obj) {
    var gameId = obj.getAttribute("name");
    var point = obj.getAttribute("value") / 2;

    var vote = votes.find(v => v.gameId == gameId);
    if (vote != null) {
        vote.point = point;
    } else {
        vote = new Vote(gameId, point);
        votes.push(vote);
    }
    console.log(Vote.toXML(vote));

    localStorage.setItem("votes", JSON.stringify(votes));
}


function search(searchValue) {
    searchValue = searchValue.toLowerCase();
    var exp = "//games[contains(translate(title,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + searchValue + "')]";
    var resultDom = Utils.applyXPath(exp, Utils.parseToXmlDom(gamesString), null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null, "board-game");

    if (!resultDom) {
        return null;
    }

    var resultXsl = new Document();
    resultXsl = Utils.applyXsl(resultDom, gameRenderXslDom, resultXsl);
    return resultXsl;
}

function loadGame(offset, fetch) {
    //get game
    var exp = "//games[position() >= " + (offset * fetch + 1) + " and position()  <= " + fetch * (offset + 1) + "]";
    var xmlDom = Utils.parseToXmlDom(gamesString);
    var resultDom = Utils.applyXPath(exp, xmlDom, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null, "board-game");
    console.log(resultDom);
    if (!resultDom.childNodes.length) {
        return;
    }
    var resultXsl = new Document();
    resultXsl = Utils.applyXsl(resultDom, gameRenderXslDom, resultXsl);
    xmlDom = Utils.parseToXmlDom(gamesString);
    if (fetch * (offset + 1) >= xmlDom.documentElement.childNodes.length - 20) {
        var param = "?offset=" + ++offsetServer + "&fetch=" + fetchServer;
        Utils.callToServer(loadMoreUrl + param, "GET", null, true, function(responseXML) {
            //save to list
            var resultDom = Utils.parseToXmlDom(gamesString);
            resultDom = Utils.mergeXMLDom(responseXML, resultDom);
            var serializer = new XMLSerializer();
            gamesString = serializer.serializeToString(resultDom.documentElement);
            console.log(resultDom.documentElement.childNodes.length);
        });
    }

    //load into game section
    var gameSection = document.getElementsByClassName("game-section")[0];
    gameSection.appendChild(resultXsl);
}

function onScroll(element, distance) {
    var scrollTop = 0;
    var clientHeight = 0;
    var scrollHeight = 0;

    if (typeof(element) == typeof(window)) {
        scrollTop = window.scrollY;
        clientHeight = window.innerHeight;
        scrollHeight = document.body.offsetHeight;

    } else {
        scrollTop = element.scrollTop;
        clientHeight = element.clientHeight;
        scrollHeight = element.scrollHeight;
    }

    if (scrollTop + clientHeight >= scrollHeight - distance) {
        offsetClient++;
        loadGame(offsetClient, fetchClient);
    }
}