<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <%
	Purchase purchase=(Purchase)request.getAttribute("purchase");
%>  --%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<form name="updatePurchase" action="/purchase/updatePurchaseView?tranNo=0" method="post">

다음과 같이 구매가 되었습니다.

<table border="1">
	<tbody><tr>
		<td>물품번호</td>
		<%-- <td><%=purchase.getPurchaseProd().getProdNo()%></td> --%>
		<td>${purchase.purchaseProd.prodNo}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<%-- <td><%=purchase.getBuyer().getUserId()%></td> --%>
		<td>${purchase.buyer.userId}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
		
			<%-- <%if(purchase.getPaymentOption().equals("1")){ %> --%>
			<c:if test="${purchase.paymentOption=='1'}">
			현금구매
		<%--<%}else{%> --%>
			</c:if>
			<c:if test="${purchase.paymentOption=='2'}">
			신용구매
		<%--<%}%> --%>
			</c:if>
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<%-- <td><%=purchase.getBuyer().getUserName()%></td> --%>
		<td>${purchase.buyer.userName}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<%-- <td><%=purchase.getReceiverPhone()%></td> --%>
		<td>${purchase.receiverPhone}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<%-- <td><%=purchase.getDivyAddr()%></td> --%>
		<td>${purchase.divyAddr}</td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<%-- <td><%=purchase.getDivyRequest()%></td> --%>
		<td>${purchase.divyRequest}</td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<%-- <td><%=purchase.getDivyDate()%></td> --%>
		<td>${purchase.divyDate}</td>
		<td></td>
	</tr>
</tbody></table>
</form>
</body>
</html>