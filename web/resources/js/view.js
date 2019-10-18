var view = {
    octopus: null,

    init: function(octopus) {
        this.octopus = octopus;
        this.initSearchForm();
        this.initOnSroll(document);
    },

    renderGame: function(gameDom) {
        var gameSection = document.getElementsByClassName("game-section")[0];
        gameSection.appendChild(gameDom);
        this.renderRatePoint();
    },

    renderRatePoint: function() {
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
    },

    renderSearchResult: function(result) {
        var gameSection = document.getElementsByClassName("game-section")[0];
        Utils.removeAllChildNodes(gameSection);
        gameSection.appendChild(result);
    },

    renderGameDetail: function(resultDom) {
        var isDisplay = true;
        var gameDetailDiv = document.getElementById("game-detail");
        //check div tồn tại thì xóa
        if (gameDetailDiv) {
            document.body.removeChild(gameDetailDiv);
        }

        document.body.insertBefore(resultDom, document.body.firstChild);

        gameDetailDiv = document.getElementById("game-detail");
        gameDetailDiv.style.display = "block";

        document.onclick = function(e) {
            if (gameDetailDiv == e.target && isDisplay) {
                gameDetailDiv.style.display = "none";
                isDisplay = false;
            }
        }
    },

    initSearchForm: function() {
        var searchForm = document.getElementById("search-form");
        if (searchForm != null) {
            searchForm.addEventListener("submit", function(e) {
                var searchValue = document.getElementById("search-input").value.trim();
                var searchForm = document.getElementById("search-form");
                octopus.processSearchFormSubmit(e, searchValue, searchForm);
            });
        }
    },

    initOnSroll: function(element) {
        element.addEventListener("scroll", function() {
            octopus.processOnScroll(element);
        });
    },

    confirm: function(msg) {
        return window.confirm(msg);
    },

    onClickGameDetail: function(element) {
        var gameId = element.getAttribute("value");
        this.octopus.processGameDetail(gameId);
    },

    onButtonSubmitSearchFormChanged: function(element) {
        var buttonSubmitSearch = element.getAttribute("value");
        this.octopus.processButtonSubmitSearchFormChanged(buttonSubmitSearch);
    },

    onBeforeUnload: function() {
        this.octopus.processBeforeUnload();
    },

    onVote: function(element) {
        var gameId = element.getAttribute("name");
        var point = element.getAttribute("value") / 2;

        this.octopus.processVote(gameId, point);
    }
};