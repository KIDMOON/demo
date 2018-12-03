<html>
<head>
    <title>WebScoket广播式</title>
    <script src="resource/assets/js/sockjs.js"></script>
    <script src="resource/assets/js/stomp.js"></script>
    <script src="resource/assets/js/jquery-1.8.2.min.js"></script>
</head>
<button id="connect" >连接</button>
<button id="disconnect">断开连接</button><br/>
<button id="clear">清除</button><br/>
<div id="inputDiv">
    输入名称：<input type="text" id="name"/><br/>
    <button id="sendName" onclick="send()">发送</button><br/>
    <p id="response"></p>
</div>
<script>
    var websocket = null;

    //判断当前浏览器是否支持WebSocket
    if('WebSocket' in window){
        var url="ws://"+window.location.host+"/messageHandler"
        websocket = new WebSocket(url);
    }
    else{
        alert('Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function(){
        setMessageInnerHTML("error");
    };

    //连接成功建立的回调方法
    websocket.onopen = function(event){
        setMessageInnerHTML("open");
    }

    //接收到消息的回调方法
    websocket.onmessage = function(event){
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function(){
        setMessageInnerHTML("close");
    }
    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function(){
        websocket.close();
    }
    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML){
        document.getElementById('response').innerHTML += innerHTML + '<br/>';
    }
    //关闭连接
    function closeWebSocket(){
        websocket.close();
    }
    //发送消息
    function send(){
        var message = document.getElementById('name').value;
        websocket.send(message);
    }

    $("#name").keyup(function(event){
        if(event.keyCode ==13){
            var message = document.getElementById('name').value;
            websocket.send(message);
            $("#name").val("");
        }
    });

    $("#clear").click(function () {
        document.getElementById('response').innerHTML = " ";
    })

</script>
</body>
</html>
