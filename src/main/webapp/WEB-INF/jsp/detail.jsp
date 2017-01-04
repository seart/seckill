<%--
  Author: hutaishi@qq.com
  Date: 2016/12/25
  Description: 11:00
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%@include file="common/bootstrap.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">${seckill.name}</div>

        <div class="panel-body">
            <h2 class="text-danger">
                <span class="glyphicon glyphicon-time"></span>
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>
</div>

<div class="modal fade" id="killPhoneMod">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title text-center">请输入手机号<span class="glyphicon glyphicon-phone"></span></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" class="form-group" id="killPhoneKey" placeholder="请输入手机号"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>Submit
                </button>
            </div>
        </div>

    </div>
</div>
</body>
<script src="/resources/js/jquery.cookie.min.js"></script>
<script src="/resources/js/jquery.countdown.min.js"></script>


<%--开始编写交互脚本--%>
<script src="/resources/script/seckill.js"></script>

<script type="text/javascript">
    $(function () {
        seckill.detail.init({
            seckillId: ${seckill.seckillId},
            startTime: ${seckill.startTime.time},
            endTime: ${seckill.endTime.time}
        });
    });
</script>
</html>