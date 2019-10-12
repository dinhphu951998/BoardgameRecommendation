var Utils = {
    convertArrayToXML: function(arr, rootTagName) {
        var xml = "<" + rootTagName + ">";
        for (var i = 0; i < arr.length; i++) {
            xml += Vote.toXML(arr[i]);
        }
        xml += "</" + rootTagName + ">";
        return xml;
    },

    applyXPath: function(expression, contextNode, namespaceResolver, resultType, xpathResult, qNameResultNode) {
        var resultSet = document.evaluate(expression, contextNode, namespaceResolver, resultType, xpathResult);
        var resultDom = document.createElement(qNameResultNode);
        var n;
        try {
            for (var i = 0; i < resultSet.snapshotLength; i++) {
                n = resultSet.snapshotItem(i);
                resultDom.appendChild(n);
            }
        } catch (e) {
            console.log(e);
        }
        return resultDom;
    },


    applyXsl: function(xmlDom, xslDom, resultDom) {
        var xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xslDom);
        resultDom = xsltProcessor.transformToFragment(xmlDom, resultDom);
        return resultDom;
    },

    parseToXmlDom: function(xmlString) {
        var doc = new DOMParser().parseFromString(xmlString, "text/xml");
        return doc;
    },

    getXMLHttpRequest: function() {
        var xmlHttp = null;
        try {
            xmlHttp = new XMLHttpRequest();

        } catch (e) {
            try {
                xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e) {
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
        }
        return xmlHttp;
    }
};