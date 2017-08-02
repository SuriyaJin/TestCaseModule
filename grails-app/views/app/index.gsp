<!DOCTYPE html>
<html>
    <head>
        <title>WebTest</title>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"rel="stylesheet">
        <link rel="stylesheet"href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/css/bootstrap-select.min.css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/js/bootstrap-select.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <style>
        input, textarea {
            width: 20%;
            border: 0;
            padding: 10px 5px;
            background: white no-repeat;
            background-image: linear-gradient(to bottom, #1abc9c, #1abc9c),
            linear-gradient(to bottom, silver, silver);
            background-size: 0 2px, 100% 1px;
            background-position: 50% 100%, 50% 100%;
            transition: background-size 0.3s cubic-bezier(0.64, 0.09, 0.08, 1);
        }

        input:focus, textarea:focus {
            background-size: 100% 2px, 100% 1px;
            outline: none;
        }

        #successMessage{
            margin-top:10%;
            text-align: center;
            font-family: monospace;
            font-size:30px;
            font-weight: 900px;
            color:#58D68D;
        }
        #text{
            width:13%;
        }
        #selectorValues{
            width:15%;
        }
        #myModal{
            position: absolute;
            max-height: calc(100vh - 210px);
            overflow-y: auto;
        }
    </style>
</head>
<body onload="show()">
    <div id="myModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <span class="close">&times;</span>
                <h2>Edit</h2>
            </div>
            <div class="modal-body">
                <div id="purchaseDetails"></div>
            </div>
            <div class="modal-footer">
                <h6>Press update to make change.</h6>
            </div>
        </div>

    </div>

    <div>
        <center>
            <div class="row-fluid" style="margin-top:5%;">
                <div class="row col-sm-11 col-xs-11 col-md-11 col-lg-11">
                    <select name="By-options" id="By-options" class="selectpicker"
                    data-show-subtext="true" data-live-search="true">
                    <option data-subtext="  Perform with element id" value="id">Id</option>
                    <option data-subtext="  Perform with element name" value="name">Name</option>
                    <option data-subtext="  Perform with css property"
                    value="cssSelector">Css Selector</option>
                    <option data-subtext="  Perform with element path" value="xpath">X-Path</option>
                    <option data-subtext="  Perform with tag name" value="tagName">Tag</option>
                    <option data-subtext="Perform with class name" value="className">Class Name</option>
                    </select> <input placeholder="Selector value" id="selectorValues"
                    type="text"> <select onchange="visible(this)"
                    style="width: 10px" name="operation" class="selectpicker"
                    data-show-subtext="true" data-live-search="true" id="operation">
                    <option data-subtext="  Click Element" value="Click">Click</option>
                    <option data-subtext="  Check Element present" value="CheckTrue">Check Element Present</option>
                    <option data-subtext="  Check Element Absent" value="CheckFalse">Check Element Absent</option>
                    <option data-subtext="  To type the text" value="Send Keys">Send Keys</option>
                    <option data-subtext="  Check the select options"
                    value="Select Options">Select Options</option>
                    <option data-subtext="  Check the content equality"
                    value="Verify Text">Verify Text</option>
                    <option data-subtext="  Check the content contains"
                    value="Contains Text">Contains Text</option>
                    </select> <input id="text" style="visibility: hidden" placeholder="Text"
                    type="text">
                    <button onclick="add()" class="btn btn-info">Add</button>
                </div>
                <div id="Addedcases" style="margin-top:5%;display:inline-block;"></div>
            </div>
        </center>
        <center>
            <div id="successMessage"></div>
            <div id="downloadFile"></div>
        </center>
    </div>
</body>
<script>
    window.onload = function() {
        var http = new XMLHttpRequest();
        http.open("GET", "app/show", true);
        http.onreadystatechange = function() {
            if(http.readyState == 4 && http.status == 200) {
                document.getElementById("Addedcases").innerHTML = http.responseText;
            }
        }
        http.send(null);
    }
    var modal = document.getElementById('myModal');
    var span = document.getElementsByClassName("close")[0];
    span.onclick = function() {
        modal.style.display = "none";
    }
    function cancel() {
        modal.style.display = "none";
    }

    function edit(id) {
        var xhttp = new XMLHttpRequest();
        var params = "id=" + id
        xhttp.open("POST", "app/edit", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("purchaseDetails").innerHTML = this.responseText;
            }
        }
        xhttp.send(params);
        modal.style.display = "block";
    }

    function visible(element) {
        var name = element.value;
        if(name ==="Verify Text" || name === "Send Keys" || name === "Select Options" || name === "Contains Text")  {
            document.getElementById("text").style.visibility = "visible";
            document.getElementById("text").placeholder = name;
        }
        else{
            document.getElementById("text").style.visibility = "hidden";
        }
    }

    function add() {
        var http = new XMLHttpRequest();
        var url;
        var selector = document.getElementById("By-options").value;
        var selectorValue = document.getElementById("selectorValues").value;
        var operation = document.getElementById("operation").value;
        var params = "selector="+selector+"&selectorValue="+selectorValue+"&operation="+operation;
        if(document.getElementById("text").style.visibility === "visible"){
            params += "&text="+document.getElementById("text").value;
            url = "app/addWithText";
        }else
            url = "app/add";
        http.open("POST", url, true);
        http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        http.onreadystatechange = function() {
            if(http.readyState == 4 && http.status == 200) {
                document.getElementById("Addedcases").innerHTML = http.responseText;
            }
        }
        http.send(params); 
    }

    function update() {
        modal.style.display = "none";
        var id = document.getElementById("hiddenField").value;
        var http = new XMLHttpRequest();
        var url = "app/update";
        var selector = document.getElementById("By-options").value;
        var selectorValue = document.getElementById("selectorValues").value;
        var action = document.getElementById("operation").value;
        var params = "id=" + id + "&selector=" + selector + "&selectorValue="
        + selectorValue + "&operation=" + action;
        if (action ==="Verify Text" || action === "Send Keys" || action === "Select Options" || action === "Contains Text") {
            params += "&text=" + document.getElementById("text").value;
            url = "app/updateWithText";
        }
        http.open("POST", url, true);
        http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        http.onreadystatechange = function() {
            if (http.readyState == 4 && http.status == 200) {
                document.getElementById("Added cases").innerHTML = http.responseText;
            }
        }
        http.send(params);
        window.location.reload();
    }

    function deleteCase(id) {
        var r = confirm("Are you sure you want to delete it?(Cannot revert it again)");
        if (r == true) {
            var http = new XMLHttpRequest();
            var url = "app/delete";
            var params = "id=" + id;
            http.open("POST", url, true);
            http.setRequestHeader("Content-type","application/x-www-form-urlencoded");
            http.onreadystatechange = function() {
                if (http.readyState == 4 && http.status == 200) {
                    document.getElementById("Added cases").innerHTML = http.responseText;
                }
            }
            http.send(params);
            window.location.reload();
        }
    }

    function generateTestCase(){
        var http = new XMLHttpRequest();
        http.open("GET","app/finish",true);
        http.onreadystatechange = function() {
            if(http.readyState == 4 && http.status == 200)
                document.getElementById("successMessage").innerHTML = http.responseText;
        }
        http.send(null);
        document.getElementById("preview").style.visibility = "visible";
    }
    
    function showPreview() {
        var http = new XMLHttpRequest();
        http.open("GET","app/showPreview",true);
        http.onreadystatechange = function() {
            if(http.readyState == 4 && http.status == 200)
                document.getElementById("purchaseDetails").innerHTML = this.responseText;
        }
        http.send(null);
        modal.style.display = "block";
    }
</script>
</html>
