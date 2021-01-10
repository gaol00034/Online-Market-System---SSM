<%@page language="java" contentType="text/html; character=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>管理员后台</title>
    <link rel="stylesheet" href="${ctx}/resource/css/pintuer.css">
    <link rel="stylesheet" href="${ctx}/resource/css/admin.css">
    <script src="${ctx}/resource/js/jquery.js"></script>
    <script src="${ctx}/resource/js/pintuer.js"></script>
    <script type="text/javascript" src="${ctx}/resource/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctx}/resource/ueditor/ueditor.all.min.js"></script>
</head>
<body>
<div class="panel admin-panel">
    <div class="panel-head" id="add">
        <strong><span class="icon-pencil-square-o">商品维护</span> </strong>
    </div>
    <div class="body-content">
        <form action="${ctx}/orderDetail/exUpdate" method="post" class="form-x" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${obj.id}" />
            <input type="hidden" name="orderId" value="${obj.orderId}" />
            <div class="form-group">
                <div class="label"><label>选择商品：</label></div>
                <div class="field">
                    <select name="itemId" class="input w50">
                        <c:forEach items="${types}" var="data" varStatus="l">
                            <option value="${data.id}">${data.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="label"><label>商品下单数目：</label></div>
                <div class="field">
                    <input type="text" class="input w50" name="num" data-validate="required:请输入数目"/>
                    <div class="tips"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="label"></div>
                <div class="field">
                    <button class="button bg-main icon-check-square-o" type="submit">提交</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>