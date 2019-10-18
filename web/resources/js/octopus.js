var octopus = {
    model: null,
    view: null,

    init: function(model, view) {
        this.model = model;
        this.view = view;

        this.model.init();
        this.view.init(this);

        this.processLoadGame(this.model.offsetClient, this.model.fetchClient);
    },

    processLoadGame: function(offset, fetch) {
        var exp = "//games[position() >= " + (offset * fetch + 1) + " and position()  <= " + fetch * (offset + 1) + "]";
        var gameXmlDom = Utils.parseToXmlDom(this.model.gamesXmlString);
        var resultGameDom = Utils.applyXPath(exp, gameXmlDom, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null, "board-game");
        console.log(resultGameDom);
        if (!resultGameDom || !resultGameDom.childNodes.length) {
            return;
        }

        var resultXsl = new Document();
        resultXsl = Utils.applyXsl(resultGameDom, this.model.gameRenderXslDom, resultXsl);
        gameXmlDom = Utils.parseToXmlDom(this.model.gamesXmlString);
        if (fetch * (offset + 1) >= gameXmlDom.documentElement.childNodes.length - 20) {
            this.processLoadMore();
        }

        //load into game section
        this.view.renderGame(resultXsl);
    },

    processLoadMore: function() {
        if (this.model.availableToLoadMore) {
            var url = this.model.loadMoreUrl.replace("offsetValue", ++this.model.offsetServer);
            url = url.replace("fetchValue", this.model.fetchServer);
            Utils.callToServer(url, "GET", null, true, function(responseXML) {
                if (responseXML) {
                    //save to list
                    var resultDom = Utils.parseToXmlDom(this.model.gamesXmlString);
                    resultDom = Utils.mergeXMLDom(responseXML, resultDom);
                    this.model.gamesXmlString = Utils.serializeToString(resultDom.documentElement);
                    console.log(resultDom.documentElement.childNodes.length);
                } else {
                    this.model.availableToLoadMore = false;
                }
            });
        }
    },

    processSearchFormSubmit: function(e, searchValue, searchForm) {
        e.preventDefault();
        if (searchValue && searchValue.length != 0) {
            if (this.model.buttonSubmitSearch == "advanced") {
                searchForm.submit();
            } else {
                var result = this.processSearch(searchValue);
                if (!result) {
                    var confirm = this.view.confirm("Click ok to perform advanced search");
                    if (confirm) {
                        searchForm.submit();
                    }
                    return;
                } //end if result not empty
                this.view.renderSearchResult(result);
            }
        } //end if search value null or empty
    },

    processSearch: function(searchValue) {
        searchValue = searchValue.toLowerCase();
        var exp = "//games[contains(translate(title,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + searchValue + "')]";
        var resultDom = Utils.applyXPath(exp,
            Utils.parseToXmlDom(this.model.gamesXmlString), null,
            XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,
            null, "board-game");
        if (!resultDom) {
            return null;
        }
        var resultXsl = new Document();
        resultXsl = Utils.applyXsl(resultDom, this.model.gameRenderXslDom, resultXsl);
        return resultXsl;
    },

    processOnScroll: function(element) {
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

        if (scrollTop + clientHeight >= scrollHeight - this.model.distance) {
            this.model.offsetClient++;
            this.processLoadGame(this.model.offsetClient, this.model.fetchClient);
        }
    },

    processGameDetail: function(gameId) {
        var url = this.model.baseUrl + "?id=" + gameId;
        Utils.callToServer(url, "GET", null, true, function(responseXML) {
            if (responseXML) {

                var resultDom = new Document();
                var resultDom = Utils.applyXsl(responseXML, this.model.gameDetailRenderXslDom, resultDom);
                console.log(resultDom);

                this.view.renderGameDetail(resultDom);
            }
        });

    },

    processButtonSubmitSearchFormChanged: function(buttonSubmitSearch) {
        this.model.buttonSubmitSearch = buttonSubmitSearch;
    },

    processBeforeUnload: function() {
        if (this.model.votes != null && this.model.votes.length > 0) {
            var url = "vote";
            var param = "xml=" + Utils.convertArrayToXML(this.model.votes, "votes");
            Utils.callToServer(url, "POST", param, false, function() {
                localStorage.removeItem("votes");
            });
        }
    },

    processVote: function(gameId, point) {
        var vote = this.model.votes.find(v => v.gameId == gameId);
        if (vote != null) {
            vote.point = point;
        } else {
            vote = new Vote(gameId, point);
            this.model.votes.push(vote);
        }
        console.log(Vote.toXML(vote));

        localStorage.setItem("votes", JSON.stringify(this.model.votes));
    }
};