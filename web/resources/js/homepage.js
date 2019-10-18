function onload() {
    this.model.baseUrl = this.baseUrl;
    this.model.setGameRenderStylesheet(this.gameRenderXsl);
    this.model.setGameDetailRenderStylesheet(this.gameDetailRenderXsl);
    this.model.gamesXmlString = this.gamesString;
    this.model.loadMoreUrl = this.loadMoreUrl;

    this.octopus.init(this.model, this.view);
}

window.onbeforeunload = function() {
    this.view.onBeforeUnload();
}

function initGameDetail(element) {
    this.view.onClickGameDetail(element);
}


function setButtonSubmitSearchForm(obj) {
    this.view.onButtonSubmitSearchFormChanged(obj);
}


function onVote(obj) {
    this.view.onVote(obj);
}