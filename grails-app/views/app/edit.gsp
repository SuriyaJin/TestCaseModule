<%@page import="com.ebox.webtest.TestCase"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link
        href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
        rel="stylesheet">
        <link rel="stylesheet"
        href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/css/bootstrap-select.min.css" />
        <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script
    src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script
src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.6.3/js/bootstrap-select.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<style>
    input,textarea {
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

    input:focus,textarea:focus {
    background-size: 100% 2px, 100% 1px;
    outline: none;
    }




    body, html { text-align:center;}

    /* The CSS */
    select {
    padding:3px;
    margin: 0;
    -webkit-border-radius:4px;
    -moz-border-radius:4px;
    border-radius:4px;
    -webkit-box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    -moz-box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    box-shadow: 0 3px 0 #ccc, 0 -1px #fff inset;
    color:#888;
    border:none;
    outline:none;
    display: inline-block;
    -webkit-appearance:none;
    -moz-appearance:none;
    appearance:none;
    cursor:pointer;
    }

    /* Targetting Webkit browsers only. FF will show the dropdown arrow with so much padding. */
    @media screen and (-webkit-min-device-pixel-ratio:0) {
    select {padding-right:18px}
    }

    label {position:relative}
    label:after {
    content:'<>';
    font:11px "Consolas", monospace;
    color:#aaa;
    -webkit-transform:rotate(90deg);
    -moz-transform:rotate(90deg);
    -ms-transform:rotate(90deg);
    transform:rotate(90deg);
    right:8px; top:2px;
    padding:0 0 2px;
    border-bottom:1px solid #ddd;
    position:absolute;
    pointer-events:none;
    }
    label:before {
    content:'';
    right:6px; top:0px;
    width:20px; height:20px;
    position:absolute;
    pointer-events:none;
    display:block;
    }

    #text{
    visibility:"hidden";
    }

</style>

<div align=center  >
    <div class="container">
        <div class="row"></div>
        <div class="row-fluid">
            <select name="By-options" id="By-options" class="selectpicker"data-show-subtext="true" data-live-search="true">
            <option data-subtext="  Perform with element id" value="id">Id</option>
            <option data-subtext="  Perform with element name" value="name">Name</option>
            <option data-subtext="  Perform with css property"
            value="cssSelector">Css Selector</option>
            <option data-subtext="  Perform with element path" value="xpath">X-Path</option>
            <option data-subtext="  Perform with tag name" value="tagName">Tag</option>
            <option data-subtext="Perform with class name" value="className">Class
                Name</option>
            </select> <input  id="selectorValues" type="text" value = "${testCase?.selectorValue}">
            <select onchange="visible(this)" 
            name="operation" class="selectpicker" id="operation">
            <g:if test = "${testCase.action == 'click'}">
            <option selected data-subtext="  Click Element" value="Click">Click</option>
            </g:if>
            <g:else><option data-subtext="  Click Element" value="Click">Click</option></g:else>
            <g:if test = "${testCase.action == 'CheckTrue'}">
            <option selected data-subtext="  Check Element present" value="CheckTrue">Check
                Element Present</option></g:if>
            <g:else><option data-subtext="  Check Element present" value="CheckTrue">Check
                Element Present</option></g:else> <g:if test = "${testCase.action == 'CheckFalse'}">
            <option selected data-subtext="  Check Element Absent" value="CheckFalse">Check
                Element Absent</option></g:if>
               <g:else>
            <option  data-subtext="  Check Element Absent" value="CheckFalse">Check
                Element Absent</option></g:else>
                <g:if test = "${testCase.action == 'Send Keys'}">
            <option selected data-subtext="  To type the text" value="Send Keys">Send
                Keys</option></g:if>
                <g:else>
            <option data-subtext="  To type the text" value="Send Keys">Send
                Keys</option></g:else>
             <g:if test = "${testCase.action == 'Select Options'}">
                <option selected data-subtext="  Check the select options"
            value="Select Options">Select Options</option></g:if>
            <g:else>
            <option data-subtext="  Check the select options"
            value="Select Options">Select Options</option></g:else>
            <g:if test = "${testCase.action == 'Verify Text'}">
            <option selected data-subtext="  Check the content equality"
            value="Verify Text">Verify Text</option></g:if>
            <g:else>
            <option data-subtext="  Check the content equality"
            value="Verify Text">Verify Text</option></g:else>
            <g:if test = "${testCase.action == 'Contains Text'}">
            <option selected data-subtext="  Check the content contains"
            value="Contains Text">Contains Text</option></g:if>
            <g:else>
            <option data-subtext="  Check the content contains"
            value="Contains Text">Contains Text</option></g:else>

            </select>
            <input  placeholder="Text" id="text" name="text" type="text" value = "${testCase.text}"/>
            <input type = "hidden" id = "hiddenField" value="${testCase.id}"/>
            <button onclick="update()" class="btn btn-info">Update</button>
            <button onclick="cancel()" class="btn btn-danger">cancel</button>
        </div>
    </div>
</div>
</body>
</html>