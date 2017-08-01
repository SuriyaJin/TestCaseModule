package com.ebox.webtest
class TestCase {
    def id
    def selector
    def selectorValue
    def action
    def isText
    def text
    
    TestCase(id,selector, selectorValue, action,isText) {
        this.id = id;
        this.selector = selector;
        this.selectorValue = selectorValue;
        this.action = action;
        this.isText = isText;
    }
    
    TestCase(id,selector, selectorValue, action, isText,text) {
        this.id = id;
        this.selector = selector;
        this.selectorValue = selectorValue;
        this.action = action;
        this.isText = isText;
        this.text = text;
    }  
}
