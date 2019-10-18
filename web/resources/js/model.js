var model = {
    votes: [],

    //pass into model
    gameRenderXslDom: null,
    gameDetailRenderXslDom: null,
    gamesXmlString: null,
    loadMoreUrl: null,
    baseUrl: null,

    buttonSubmitSearch: "",
    //client
    offsetClient: 0,
    fetchClient: 20,
    //server
    offsetServer: 0,
    fetchServer: 100,

    distance: 200, // 200px
    availableToLoadMore: true,

    init: function() {

        //init votes
        var voteString = localStorage.getItem("votes");
        this.votes = JSON.parse(voteString);
        if (this.votes == null) {
            this.votes = [];
        }
    },

    setGameRenderStylesheet: function(gameRenderXslString) {
        this.gameRenderXslDom = Utils.parseToXmlDom(gameRenderXslString);
    },
    setGameDetailRenderStylesheet: function(gameDetailRenderXslString) {
        this.gameDetailRenderXslDom = Utils.parseToXmlDom(gameDetailRenderXslString);
    },

};