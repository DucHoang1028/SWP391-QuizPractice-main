<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="./Assets/Images/Common/logo.ico">
        <link rel="stylesheet" href="./Assets/Styles/Sale/RegistrationDetails.css"/>
        <title>Registration Details</title>
        <script src="./Assets/Js/Sales/mainDetails.js"></script>
    </head>
    <body>
        <c:set scope="request" var="currentPage" value="Dashboard"></c:set>
        <jsp:include page="../Common/SideBarView.jsp"></jsp:include> 
        <form action="registration-details" method="POST">
            <input type="hidden" value="${param.action}" name="action">
           <input type="hidden" value="${requestScope.registration.registrationId}" name="id">
         
                <div class="main">
                    <div class="header">
                        <div class="header__title">
                            <div class="header__title-text">Registration #${requestScope.registration.registrationId} from 
                            ${requestScope.registration.userEmaill}</div>
                        <div class="header__title-subtext">View and edit registration detail</div>
                    </div>
                    <div class="header__action">
                        <div class="header__action-add"><svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 14 14" fill="none">
                            <path d="M1 7H7M7 7H13M7 7V13M7 7V1" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>Add New</div>
                        <button type="submit" class="header__action-save">
                            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18" fill="none">
                            <path d="M17.71 6.29L11.71 0.29C11.6178 0.200049 11.5092 0.128743 11.39 0.0799999C11.266 0.0296169 11.1338 0.00249808 11 0H3C2.20435 0 1.44129 0.316071 0.87868 0.87868C0.316071 1.44129 0 2.20435 0 3V15C0 15.7956 0.316071 16.5587 0.87868 17.1213C1.44129 17.6839 2.20435 18 3 18H15C15.7956 18 16.5587 17.6839 17.1213 17.1213C17.6839 16.5587 18 15.7956 18 15V7C18.0008 6.86839 17.9755 6.73793 17.9258 6.61609C17.876 6.49426 17.8027 6.38344 17.71 6.29ZM6 2H10V4H6V2ZM12 16H6V13C6 12.7348 6.10536 12.4804 6.29289 12.2929C6.48043 12.1054 6.73478 12 7 12H11C11.2652 12 11.5196 12.1054 11.7071 12.2929C11.8946 12.4804 12 12.7348 12 13V16ZM16 15C16 15.2652 15.8946 15.5196 15.7071 15.7071C15.5196 15.8946 15.2652 16 15 16H14V13C14 12.2044 13.6839 11.4413 13.1213 10.8787C12.5587 10.3161 11.7956 10 11 10H7C6.20435 10 5.44129 10.3161 4.87868 10.8787C4.31607 11.4413 4 12.2044 4 13V16H3C2.73478 16 2.48043 15.8946 2.29289 15.7071C2.10536 15.5196 2 15.2652 2 15V3C2 2.73478 2.10536 2.48043 2.29289 2.29289C2.48043 2.10536 2.73478 2 3 2H4V5C4 5.26522 4.10536 5.51957 4.29289 5.70711C4.48043 5.89464 4.73478 6 5 6H11C11.2652 6 11.5196 5.89464 11.7071 5.70711C11.8946 5.51957 12 5.26522 12 5V3.41L16 7.41V15Z" fill="white"/>
                            </svg>Save Changes</button>
                        <a href="registration-list"><div class="header__action-close"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none">
                                <path d="M19 19L10 10M10 10L1 1M10 10L19.0001 1M10 10L1 19.0001" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                </svg>Cancel</div></a>
                    </div>
                </div>
                            <h1>${param.message}</h1>
                <div class="registration">
                    <div class="registration-title">Registration details</div>
                    <div class="registration-column">
                        <div class="registration-info">
                            <div class="info">
                                <div class="info__label">Subject</div>
                                <div class="info__input">
                                    <select disabled>
                                        <option>${requestScope.registration.subjectName}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="info">
                                <div class="info__label">Price Package</div>
                                <div class="info__input">
                                    <select disabled>
                                        <option>${requestScope.registration.packageName}</option>                 
                                    </select>
                                </div>
                            </div>
                            <div class="info">
                                <div class="info__label">Sale program</div>
                                <div class="info__input">
                                    <select disabled>
                                        <option>Subject 1</option>
                                        <option>Subject 2</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="registration-info">
                            <div class="info" >
                                <div class="info__label">List price</div>
                                <div class="info__input">
                                    <input disabled value="${requestScope.registration.listPrice}">
                                </div>
                            </div>
                            <div class="info">
                                <div class="info__label">Sale price</div>
                                <div class="info__input">
                                    <input disabled value="${requestScope.registration.totalCost}">
                                </div>
                            </div>
                        </div>
                        <div class="registration-info">
                            <div class="info">
                                <div class="info__label">Registration time</div>
                                <div class="info__input">
                                    <input disabled type="date" value="${requestScope.registration.registrationTime.toLocalDate().toString()}">
                                </div>
                            </div>
                            <div class="info">
                                <div class="info__label">Valid from</div>
                                <div disabled class="info__input">
                                    <input type="date" value="${requestScope.registration.getValidFrom().toLocalDate().toString()}">
                                </div>
                            </div>
                            <div class="info">
                                <div class="info__label">Valid to</div>
                                <div disabled class="info__input">
                                    <input type="date" value="${requestScope.registration.getValidTo().toLocalDate().toString()}">
                                </div>
                            </div>
                        </div>
                        <div class="registration-info">
                            <div class="info">
                                <div class="info__label">Status</div>
                                <div class="info__input">
                                    <select name="status">
                                        <c:if test="${requestScope.registration.status == 0}"><option selected value=0>Pending</option></c:if>
                                        <c:if test="${requestScope.registration.status != 0}"><option value=0>Pending</option></c:if>
                                        <c:if test="${requestScope.registration.status == 1}"><option selected value=1>Paid</option></c:if>
                                        <c:if test="${requestScope.registration.status != 1}"><option value=1>Paid</option></c:if>
                                        <c:if test="${requestScope.registration.status == 2}"><option selected value=2>Canceled</option></c:if>
                                        <c:if test="${requestScope.registration.status != 2}"><option value=2>Canceled</option></c:if>
                                         </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="customer">
                        <div class="registration-title">Customer details</div>
                        <div class="registration-column">
                            <div class="registration-info">
                                <div class="info">
                                    <div class="info__label">Full name</div>
                                    <div class="info__input">
                                        <select disabled>
                                            <option>${requestScope.customer.full_name}</option>

                                    </select>
                                </div>
                            </div>
                            <div class="info">
                                <div class="info__label">Gender</div>
                                <div class="info__input">
                                    <select disabled>
                                        <c:if test="${requestScope.customer.gender == 1}">  <option value="1" selected>Male</option></c:if>
                                        <c:if test="${requestScope.customer.gender != 1}">  <option value="1">Male</option></c:if>
                                        <c:if test="${requestScope.customer.gender == 0}">  <option value="0" selected>Female</option></c:if>
                                        <c:if test="${requestScope.customer.gender != 0}">  <option value="0">Female</option></c:if>

                                        </select>
                                    </div>
                                </div>

                            </div>
                            <div class="registration-info">
                                <div class="info">
                                    <div class="info__label">Email</div>
                                    <div class="info__input">
                                        <input disabled value="${requestScope.registration.userEmaill}">
                                </div>
                            </div>
                            <div class="info">
                                <div class="info__label">Mobile</div>
                                <div class="info__input" >
                                    <input disabled value="${requestScope.customer.mobile}">
                                </div>
                            </div>
                        </div>

                        <div class="registration-info">
                            <div class="info">
                                <div class="info__label">Note</div>
                                <div class="info__input2">
                                    <textarea name="notes">
                                        ${requestScope.registration.note.trim()}
                                    </textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
