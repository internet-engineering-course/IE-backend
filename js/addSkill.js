var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
function reqListener () {
    console.log(this.responseText);
}

params = {
    "skills": [
        {
            "name": "SQL",
            "point": 0
        }
    ]
};

var oReq = new XMLHttpRequest();
oReq.addEventListener("load", reqListener);
oReq.open("PUT", "http://localhost:8080/user");
oReq.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
oReq.send(JSON.stringify(params));