var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
function reqListener () {
    console.log(this.responseText);
}

var oReq = new XMLHttpRequest();
oReq.addEventListener("load", reqListener);
oReq.open("GET", "http://localhost:8080/project");
oReq.send();