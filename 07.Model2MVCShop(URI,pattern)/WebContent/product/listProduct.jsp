<%@page import="com.model2.mvc.common.Page"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetList(currentPage) {
	document.getElementById('currentPage').value=currentPage;
	document.detailForm.submit();
}
function fncFindProductList(){
	document.detailForm.submit();
}
function fncSortingList(orderType){
	document.getElementById('orderType').value=orderType;
	document.detailForm.submit();
}
function fncSortingByUser(pageSize){
	document.getElementById('pageSize').value=pageSize;
	document.detailForm.submit();
}


</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/listProduct" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
						<c:if test="${param.menu=='search'}">
							상품 목록조회
						</c:if>
						<c:if test="${param.menu=='manage'}">
							상품 관리
						</c:if>
									
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<input type='hidden' id="orderType" name='orderType' value="${search.orderType}"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td>
			<a href="/product/getProduct?prodNo=
				${discount.discountProd}
				&menu=
				<c:if test="${param.menu=='search'}">
				search
				</c:if>
				<c:if test="${param.menu=='manage'}">
				manage
				</c:if>">할인상품보기</a>
		</td>
		<td align="right">
		<select name="pageSize">
			<option value="${resultPage.pageSize}" >선택</option>
			<c:forEach var="i" begin="1" end="10">
			<option value="${i}" ${i==resultPage.pageSize ? "selected":""}>${i}</option>
			</c:forEach>
		</select>개씩 보기&nbsp;&nbsp;&nbsp;
		<a href="javascript:fncSortingList('1')" id="orderLast" name="orderLast" >최근등록순</a>
		<a href="javascript:fncSortingList('2')" id="orderHigh" name="orderHigh" >가격높은순</a>
		<a href="javascript:fncSortingList('3')" id="orderLow" name="orderLow">가격낮은순</a>
			<select id="searchCondition" name="searchCondition" class="ct_input_g" style="width:80px">
				
				<c:if test="${!empty search.searchCondition}">
				<c:choose>
					<c:when test="${sessionScope.user.userId == 'admin'}">					
					<option value="0" ${!empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
					</c:when>					
				</c:choose>
					<option value="1" ${!empty search.searchCondition && search.searchCondition==1 ? "selected":""}>상품명</option>
					<option value="2" ${!empty search.searchCondition && search.searchCondition==2 ? "selected":""}>상품가격</option>
				</c:if>
			
				<c:if test="${empty search.searchCondition}">
				<c:choose>
					<c:when test="${sessionScope.user.userId == 'admin' }">
					<option value="" >--선택--</option>
					</c:when>
				</c:choose>
					<option value="0" >상품번호</option>
					<option value="1" >상품명</option>
					<option value="2" >상품가격</option>
				</c:if>
			</select>
			<input type="text" id="searchKeyword" name="searchKeyword"  class="ct_input_g" style="width:200px; height:19px" 
				value="${!empty search.searchKeyword ? search.searchKeyword:''}"
				<%-- <c:if test="${!empty search.searchKeyword }">
					value="${search.searchKeyword}"
				</c:if> --%>
			/>
		</td>
	
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncFindProductList();">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>		
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var="i" value="0"/>
	<c:forEach var="product" items="${list}" >
	<c:set var="i" value="${i+1}"/>
	<tr class="ct_list_pop">
		<td align="center">${i}</td>
		<td></td>
				
				<td align="left">
				<a href="/product/getProduct?prodNo=
				${product.prodNo}
				&menu=
				<c:if test="${param.menu=='search'}">
				search
				</c:if>
				<c:if test="${param.menu=='manage'}">
				manage
				</c:if>">
				${product.prodName}</a></td>
		<td></td>
		<td align="left">${product.price}
			<c:if test="${product.prodNo == discount.discountProd}">
			<img src="https://static1.squarespace.com/static/513f57ebe4b0970eaf232dec/t/5654b2eae4b05e28e38285cd/1448391403995/" 
											style="height:20px;width:20px;">
			</c:if>
		</td>
		<td></td>
		<td align="left">${product.regDate}</td>
		<td></td>
		<td align="left">
		
			
			<c:if test="${empty product.proTranCode || product.proTranCode=='1' || product.proTranCode eq '5'}">
			판매중
			</c:if>
			<c:if test="${product.proTranCode=='2'}">
			${sessionScope.user.userId == 'admin' ? "구매완료":"재고없음"} 
			</c:if>
				<c:if test="${param.menu=='manage' && product.proTranCode=='2'}">
					<a href="/purchase/updateTranCodeByProd?prodNo=
					${product.prodNo}
					&tranCode=3">배송하기</a>
				</c:if>
			<c:if test="${product.proTranCode=='3'}">
			${sessionScope.user.userId == 'admin' ? "배송중":"재고없음"}
			</c:if>
			<c:if test="${product.proTranCode=='4'}">
			재고없음
			</c:if>
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	</c:forEach>
	
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="menu" name="menu" value="${param.menu}"/>
		<input type="hidden" id="currentPage" name="currentPage" value="${search.currentPage}"/>
			<jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
