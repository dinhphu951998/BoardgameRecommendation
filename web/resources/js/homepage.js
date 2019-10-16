var votes = [];
// var gamesDom;

var gameRenderXslDom;
var gameDetailRenderXslDom;
var offsetClient = 0;
var fetchClient = 20;
var offsetServer = 0;
var fetchServer = 100;
var distance = 200; // 100px
var availableToLoadMore = true;
var buttonSubmitSearch = "";

function initRatePoint() {

    var rateFieldSet = document.querySelectorAll("fieldset[value]");
    if (rateFieldSet) {
        for (var i = 0; i < rateFieldSet.length; i++) {
            var fieldset = rateFieldSet[i];
            var point = fieldset.getAttribute("value") * 2;
            var resultRadio = fieldset.querySelector('input[value="' + point + '"]');
            if (resultRadio) {
                resultRadio.checked = true;
            }
        }
    }
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

    //init rate point, đã init khi load game
    // initRatePoint();

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

    var resultDom = new Document();
    var resultDom = Utils.applyXsl(gameDetailXmlDom, gameDetailRenderXslDom, resultDom);
    console.log(resultDom);

    document.body.insertBefore(resultDom, document.body.firstChild);

    gameDetailDiv = document.getElementById("game-detail");
    gameDetailDiv.style.display = "block";

    document.onclick = function(e) {
        if (gameDetailDiv == e.target && isDisplay) {
            gameDetailDiv.style.display = "none";
            isDisplay = false;
        }
        // count++;
    }

}

function onFormSubmit(e) {
    e.preventDefault();

    var searchValue = document.getElementById("search-input").value.trim();
    if (searchValue && searchValue.length != 0) {
        if (buttonSubmitSearch == "advanced") {
            document.getElementById("search-form").submit();
        } else {
            var result = search(searchValue);
            if (!result) {
                var confirm = window.confirm("Click ok to perform advanced search");
                if (confirm) {
                    document.getElementById("search-form").submit();
                }
                return;
            } //end if result not empty
            var gameSection = document.getElementsByClassName("game-section")[0];
            Utils.removeAllChildNodes(gameSection);
            gameSection.appendChild(result);
        }
    } //end if search not empty
}

function setButtonSubmitSearchForm(obj) {
    buttonSubmitSearch = obj.getAttribute("value");
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
    if (!resultDom || !resultDom.childNodes.length) {
        return;
    }
    var resultXsl = new Document();
    resultXsl = Utils.applyXsl(resultDom, gameRenderXslDom, resultXsl);
    xmlDom = Utils.parseToXmlDom(gamesString);
    if (fetch * (offset + 1) >= xmlDom.documentElement.childNodes.length - 20) {
        loadMore();
    }

    //load into game section
    var gameSection = document.getElementsByClassName("game-section")[0];
    gameSection.appendChild(resultXsl);
    initRatePoint();
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

function loadMore() {
    if (availableToLoadMore) {
        var url = loadMoreUrl.replace("offsetValue", ++offsetServer);
        url = url.replace("fetchValue", fetchServer);
        Utils.callToServer(url, "GET", null, true, function(responseXML) {
            if (responseXML) {
                //save to list
                var resultDom = Utils.parseToXmlDom(gamesString);
                resultDom = Utils.mergeXMLDom(responseXML, resultDom);
                var serializer = new XMLSerializer();
                gamesString = serializer.serializeToString(resultDom.documentElement);
                console.log(resultDom.documentElement.childNodes.length);
            } else {
                availableToLoadMore = false;
            }
        });

    }
}