/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
 function prefillContactInfo(user) {
            console.log("hello world")
            if (user) {
                document.getElementById('fullName').value = user.fullName;
                document.getElementById('email').value = user.email;
                document.getElementById('mobileNumber').value = user.mobileNumber;
                document.getElementById('gender').value = user.gender;
            }
        }

