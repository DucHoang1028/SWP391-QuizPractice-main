<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Slider Management</title>
    <link rel="stylesheet" href="./Assets/Styles/Marketing/SliderList.css">
    <link rel="icon" href="./Assets/Images/Common/logo.ico">
    <link rel="stylesheet" href="./Assets/Styles/main.css">
   
    
  
</head>
<body>
    <c:set scope="request" var="currentPage" value="Practices"/>
        <!-- Include the header view -->
        <jsp:include page="../Common/HeaderView.jsp"></jsp:include>
            <main class="main-content">
                <header class="header">
                    <!-- Filter and sort sections are grouped together for better vertical alignment -->
        <div class="filter-sort-area">
                <div class="filter-by">
                    <label for="status-filter">Filter by Status:</label>
                    <select id="status-filter">
                        <option value="all">All</option>
                        <option value="show">Show</option>
                        <option value="hide">Hide</option>
                    </select>
                </div>
                <div class="controls">
                    <div class="slider-controls">
                        <div class="actions">
                            
                            <input type="text" class="search-input" placeholder="Search by title or backlink">
                            <button class="btn">Search</button>
                            <button class="btn">New Practice</button>
                        </div>
                        
                                                                
                            
                    </div>
                </div>
            </div>
                       
              
        
                </header>
                <section class="practice-list">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Image</th>
                                <th>Link</th>
                                <th>Status</th>                                
                            </tr>
                        </thead>
              <tbody>

                           <c:forEach var="slider" items="${sliders}">
                        <tr class="slider-item">
                            <td>${slider.id}</td>
                            <td>${slider.title}</td>
                            <td><img src="${slider.image}" alt="${slider.title}" class="slider-image"></td>
                            <td>${slider.backlink}</td>
                            <td>
                                ${slider.status == 1 ? 'Show' : 'Hide'}
                            </td>
                            <td>
                                <a href="SliderDetailsController?id=${slider.slider_id}">Details</a></button>
                            </td>
                        </tr>
                    </c:forEach>
                        </tbody>
        <div id="slider-list" class="slider-list">
            <!-- Slider items will be inserted here by JavaScript -->
        </div>
                </table>
                </section>
        
            </main>            
 
        <script src="./Assets/Js/Pagination.js"></script>
        <script src="./Assets/Js/SliderList.js"></script>
</body>

</html>
