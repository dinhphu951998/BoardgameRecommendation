
var Utils = {
    convertArrayToXML: function (arr, rootTagName) {
        var xml = "<" + rootTagName + ">";
        for (var i = 0; i < arr.length; i++) {
            xml += Vote.toXML(arr[i]);
        }
        xml += "</" + rootTagName + ">";
        return xml;
    }

};