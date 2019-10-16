class Utils {
    static convertArrayToXML(arr, rootTagName) {
        var xml = "<" + rootTagName + ">";
        for (var i = 0; i < arr.length; i++) {
            xml += Vote.toXML(arr[i]);
        }
        xml += "</" + rootTagName + ">";
        return xml;
    }

    static applyXPath(expression, contextNode, namespaceResolver, resultType, xpathResult, qNameResultNode) {
        var resultSet = document.evaluate(expression, contextNode, namespaceResolver, resultType, xpathResult);
        var resultDom = document.createElement(qNameResultNode);
        if (resultSet.snapshotLength) {
            try {
                for (var i = 0; i < resultSet.snapshotLength; i++) {
                    var n = resultSet.snapshotItem(i);
                    resultDom.appendChild(n);
                }
            } catch (e) {
                console.log(e);
            }
            return resultDom;
        }
        return null;
    }


    static applyXsl(xmlDom, xslDom, resultDom) {
        if (xmlDom && xslDom) {
            var xsltProcessor = new XSLTProcessor();
            xsltProcessor.importStylesheet(xslDom);
            resultDom = xsltProcessor.transformToFragment(xmlDom, resultDom);
        }
        return resultDom;
    }

    static parseToXmlDom(xmlString) {
        var doc;
        if (xmlString) {
            doc = new DOMParser().parseFromString(xmlString, "text/xml");
        }
        return doc;
    }

    static getXMLHttpRequest() {
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

    static callToServer(url, httpMethod, parameters, async, callback) {
        var xhr = Utils.getXMLHttpRequest();
        if (xhr == null) {
            alert("The browser not support XML HTTP");
            return;
        }
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                callback(xhr.responseXML);
            }
        };
        xhr.open(httpMethod, url, async);
        if (httpMethod.toLowerCase() == "post") {
            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        }
        xhr.send(parameters);
    }

    static mergeXMLDom(firstDom, resultDom) {
        if (!firstDom) {
            return resultDom;
        }
        if (!resultDom) {
            return null;
        }
        var node;
        while (firstDom.documentElement.hasChildNodes()) {
            node = firstDom.documentElement.childNodes[0];
            resultDom.documentElement.appendChild(node);
        }
        return resultDom;
    }

    static removeAllChildNodes(xmlDom) {
        var childs = xmlDom.childNodes;
        if (!childs) {
            childs = xmlDom.documentElement.childNodes;
        }
        while (childs.length > 0) {
            xmlDom.removeChild(childs[0]);
        }
    }

}