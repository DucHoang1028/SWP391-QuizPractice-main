-- Sample data for [dbo].[Category]
INSERT INTO [dbo].[Category] ([category_name]) VALUES
('Technology'),
('Health'),
('Finance'),
('Education'),
('Entertainment'),
('Travel'),
('Lifestyle'),
('Food'),
('Science'),
('Sports');

-- Sample data for [dbo].[User]
INSERT INTO [dbo].[User] ([full_name], [gender], [email], [mobile], [password_hash], [role], [avatar], [created_at], [updated_at], [status], [locked_until]) VALUES
('John Doe', 1, 'long6athcskl@gmail.com', '1234567890', '202cb962ac59075b964b07152d234b70', 1, NULL, GETDATE(), NULL, 1, NULL),
('Jane Smith', 0, 'nguyenthanhlong12102004vp@gmail.com', '0987654321', '202cb962ac59075b964b07152d234b70', 2, NULL, GETDATE(), NULL, 1, NULL),
('Alice Johnson', 0, 'longnthe186590@fpt.edu.vn', '1231231234', '202cb962ac59075b964b07152d234b70', 1, NULL, GETDATE(), NULL, 1, NULL),
('Bob Brown', 1, 'long17a4k23@cvp.vn', '3213214321', '202cb962ac59075b964b07152d234b70', 2, NULL, GETDATE(), NULL, 1, NULL),
('Charlie White', 1, 'pizzathanhlong@gmail.com', '2132132134', '202cb962ac59075b964b07152d234b70', 1, NULL, GETDATE(), NULL, 1, NULL);

-- Sample data for [dbo].[Post]
INSERT INTO [dbo].[Post] ([thumbnail], [title], [author_id], [content], [summary], [created_at], [updated _at], [is_featured], [status], [category_id]) VALUES
('Assets/Images/Post/thumb1.jpg', 'Tech Trends 2024', 2, 'Content for tech trends 2024', 'Summary of tech trends', GETDATE(), NULL, 1, 1, 1),
('Assets/Images/Post/thumb2.jpg', 'Health Tips', 2, 'Content for health tips', 'Summary of health tips', GETDATE(), NULL, 0, 1, 2),
('Assets/Images/Post/thumb3.jpg', 'Finance Guide', 2, 'Content for finance guide', 'Summary of finance guide', GETDATE(), NULL, 1, 1, 3),
('Assets/Images/Post/thumb4.jpg', 'Educational Insights', 4, 'Content for educational insights', 'Summary of educational insights', GETDATE(), NULL, 0, 1, 4),
('Assets/Images/Post/thumb5.jpg', 'Entertainment News', 4, 'Content for entertainment news', 'Summary of entertainment news', GETDATE(), NULL, 1, 1, 5),
('Assets/Images/Post/thumb6.jpg', 'Travel Destinations', 4, 'Content for travel destinations', 'Summary of travel destinations', GETDATE(), NULL, 0, 1, 6),
('Assets/Images/Post/thumb7.jpg', 'Lifestyle Hacks', 4, 'Content for lifestyle hacks', 'Summary of lifestyle hacks', GETDATE(), NULL, 1, 1, 7),
('Assets/Images/Post/thumb8.jpg', 'Food Recipes', 2, 'Content for food recipes', 'Summary of food recipes', GETDATE(), NULL, 0, 1, 8),
('Assets/Images/Post/thumb9.jpg', 'Science Discoveries', 4, 'Content for science discoveries', 'Summary of science discoveries', GETDATE(), NULL, 1, 1, 9),
('Assets/Images/Post/thumb10.jpg', 'Sports Highlights', 2, 'Content for sports highlights', 'Summary of sports highlights', GETDATE(), NULL, 0, 1, 10);

-- Sample data for [dbo].[Subject]
INSERT INTO [dbo].[Subject] 
([thumbnail], [title], [tag_line], [status], [created_at], [updated_at], [description], [is_featured], [category_id]) 
VALUES
('Assets/Images/Subject/thumb1.jpg', 'Introduction to Programming', 'Learn the basics of programming', 1, GETDATE(), GETDATE(), 'This course covers the fundamental concepts of programming using various languages such as Python, Java, and C++.', 1, 1),
('Assets/Images/Subject/thumb2.jpg', 'Advanced Data Science', 'Deep dive into data science techniques', 1, GETDATE(), GETDATE(), 'Explore advanced topics in data science including machine learning, deep learning, and big data analytics.', 0, 9),
('Assets/Images/Subject/thumb3.jpg', 'Health and Wellness', 'Tips for a healthier lifestyle', 1, GETDATE(), GETDATE(), 'Learn about nutrition, exercise, mental health, and other aspects of maintaining a healthy lifestyle.', 1, 2),
('Assets/Images/Subject/thumb4.jpg', 'Financial Planning', 'Manage your finances effectively', 1, GETDATE(), GETDATE(), 'This course provides strategies for personal finance management, investment planning, and saving for retirement.', 0, 3),
('Assets/Images/Subject/thumb5.jpg', 'Modern Web Development', 'Build responsive websites', 1, GETDATE(), GETDATE(), 'Learn to create modern, responsive websites using HTML, CSS, JavaScript, and popular frameworks like React and Angular.', 1, 1),
('Assets/Images/Subject/thumb6.jpg', 'Creative Writing', 'Unleash your creativity', 1, GETDATE(), GETDATE(), 'Develop your writing skills and learn techniques to craft compelling stories and narratives.', 0, 5),
('Assets/Images/Subject/thumb7.jpg', 'Photography Basics', 'Capture stunning photos', 1, GETDATE(), GETDATE(), 'Learn the fundamentals of photography, including camera settings, composition, and lighting.', 1, 4),
('Assets/Images/Subject/thumb8.jpg', 'Digital Marketing', 'Boost your online presence', 1, GETDATE(), GETDATE(), 'Understand digital marketing strategies including SEO, social media marketing, and email campaigns.', 0, 8),
('Assets/Images/Subject/thumb9.jpg', 'Business Management', 'Lead and manage effectively', 1, GETDATE(), GETDATE(), 'Gain essential skills for managing and leading a business, including strategy, operations, and leadership.', 1, 3),
('Assets/Images/Subject/thumb10.jpg', 'Graphic Design', 'Create stunning visuals', 1, GETDATE(), GETDATE(), 'Learn the principles of design and how to use tools like Photoshop and Illustrator.', 0, 4),
('Assets/Images/Subject/thumb11.jpg', 'Public Speaking', 'Enhance your speaking skills', 1, GETDATE(), GETDATE(), 'Develop confidence and learn techniques for effective public speaking and presentations.', 1, 5),
('Assets/Images/Subject/thumb12.jpg', 'History of Art', 'Explore art history', 1, GETDATE(), GETDATE(), 'Dive into the history of art from ancient times to the modern era.', 0, 6),
('Assets/Images/Subject/thumb13.jpg', 'Cybersecurity Fundamentals', 'Protect digital information', 1, GETDATE(), GETDATE(), 'Learn about cybersecurity principles, threats, and best practices for protecting information.', 1, 7),
('Assets/Images/Subject/thumb14.jpg', 'Artificial Intelligence', 'Explore AI technologies', 1, GETDATE(), GETDATE(), 'Understand the basics of AI, machine learning, and their applications in various fields.', 0, 9),
('Assets/Images/Subject/thumb15.jpg', 'Project Management', 'Manage projects effectively', 1, GETDATE(), GETDATE(), 'Learn techniques for planning, executing, and closing projects successfully.', 1, 3),
('Assets/Images/Subject/thumb16.jpg', 'Ethical Hacking', 'Learn ethical hacking', 1, GETDATE(), GETDATE(), 'Explore the world of ethical hacking and learn how to identify and fix security vulnerabilities.', 0, 7),
('Assets/Images/Subject/thumb17.jpg', 'Music Theory', 'Understand music theory', 1, GETDATE(), GETDATE(), 'Gain a comprehensive understanding of music theory, including scales, chords, and composition.', 1, 6),
('Assets/Images/Subject/thumb18.jpg', 'Environmental Science', 'Study the environment', 1, GETDATE(), GETDATE(), 'Learn about environmental issues and sustainability practices.', 0, 10),
('Assets/Images/Subject/thumb19.jpg', 'Mobile App Development', 'Develop mobile apps', 1, GETDATE(), GETDATE(), 'Learn to create mobile applications for iOS and Android using various tools and frameworks.', 1, 1),
('Assets/Images/Subject/thumb20.jpg', 'Social Psychology', 'Explore social behavior', 1, GETDATE(), GETDATE(), 'Study how people’s thoughts, feelings, and behaviors are influenced by the actual or imagined presence of others.', 0, 2),
('Assets/Images/Subject/thumb21.jpg', 'Machine Learning', 'Learn machine learning', 1, GETDATE(), GETDATE(), 'Understand machine learning algorithms and their applications in various fields.', 1, 9),
('Assets/Images/Subject/thumb22.jpg', 'Philosophy of Mind', 'Explore the mind', 1, GETDATE(), GETDATE(), 'Study the nature of the mind, consciousness, and mental functions.', 0, 6),
('Assets/Images/Subject/thumb23.jpg', 'Yoga and Meditation', 'Practice yoga and meditation', 1, GETDATE(), GETDATE(), 'Learn techniques for yoga and meditation to improve physical and mental well-being.', 1, 2),
('Assets/Images/Subject/thumb24.jpg', 'Cooking Basics', 'Learn to cook', 1, GETDATE(), GETDATE(), 'Understand basic cooking techniques and recipes to create delicious meals.', 0, 10),
('Assets/Images/Subject/thumb25.jpg', 'Blockchain Technology', 'Understand blockchain', 1, GETDATE(), GETDATE(), 'Learn the principles of blockchain technology and its applications.', 1, 7),
('Assets/Images/Subject/thumb26.jpg', 'Interior Design', 'Design beautiful spaces', 1, GETDATE(), GETDATE(), 'Gain knowledge in interior design principles and practices.', 0, 4),
('Assets/Images/Subject/thumb27.jpg', 'Data Analytics', 'Analyze data effectively', 1, GETDATE(), GETDATE(), 'Learn data analysis techniques and tools for making data-driven decisions.', 1, 9),
('Assets/Images/Subject/thumb28.jpg', 'Astronomy', 'Explore the universe', 1, GETDATE(), GETDATE(), 'Study the stars, planets, and other celestial objects in the universe.', 0, 10),
('Assets/Images/Subject/thumb29.jpg', 'Creative Photography', 'Enhance your photography skills', 1, GETDATE(), GETDATE(), 'Learn advanced photography techniques and creative composition.', 1, 4),
('Assets/Images/Subject/thumb30.jpg', 'Economics', 'Understand economic principles', 1, GETDATE(), GETDATE(), 'Gain insights into microeconomics and macroeconomics principles and theories.', 0, 3),
('Assets/Images/Subject/thumb31.jpg', 'Entrepreneurship', 'Start your own business', 1, GETDATE(), GETDATE(), 'Learn the fundamentals of starting and running a successful business.', 1, 3),
('Assets/Images/Subject/thumb32.jpg', 'Robotics', 'Learn robotics', 1, GETDATE(), GETDATE(), 'Understand the basics of robotics, including design, programming, and applications.', 0, 7),
('Assets/Images/Subject/thumb33.jpg', 'Film Production', 'Create your own films', 1, GETDATE(), GETDATE(), 'Learn the process of film production from pre-production to post-production.', 1, 4),
('Assets/Images/Subject/thumb34.jpg', 'Marketing Strategies', 'Develop effective marketing strategies', 1, GETDATE(), GETDATE(), 'Learn how to create and implement successful marketing strategies.', 0, 8),
('Assets/Images/Subject/thumb35.jpg', 'Personal Development', 'Enhance your skills', 1, GETDATE(), GETDATE(), 'Focus on personal growth and development to achieve your goals.', 1, 2),
('Assets/Images/Subject/thumb36.jpg', 'Civil Engineering', 'Understand civil engineering', 1, GETDATE(), GETDATE(), 'Learn the fundamentals of civil engineering and its applications in infrastructure development.', 0, 10),
('Assets/Images/Subject/thumb37.jpg', 'Nutrition and Dietetics', 'Understand nutrition', 1, GETDATE(), GETDATE(), 'Study the science of nutrition and dietetics to promote health and well-being.', 1, 2),
('Assets/Images/Subject/thumb38.jpg', 'Literature', 'Explore classic and modern literature', 1, GETDATE(), GETDATE(), 'Dive into the world of literature, analyzing works from different periods and genres.', 0, 6),
('Assets/Images/Subject/thumb39.jpg', 'Graphic Animation', 'Create animated graphics', 1, GETDATE(), GETDATE(), 'Learn the techniques and tools for creating graphic animations.', 1, 4),
('Assets/Images/Subject/thumb40.jpg', 'Game Development', 'Develop your own games', 1, GETDATE(), GETDATE(), 'Understand the process of game development from concept to launch.', 0, 1),
('Assets/Images/Subject/thumb41.jpg', 'Speech Therapy', 'Learn speech therapy techniques', 1, GETDATE(), GETDATE(), 'Study the principles and practices of speech therapy to help individuals with communication disorders.', 1, 2),
('Assets/Images/Subject/thumb42.jpg', 'Sociology', 'Explore social structures', 1, GETDATE(), GETDATE(), 'Understand the study of social behavior, institutions, and structures.', 0, 6),
('Assets/Images/Subject/thumb43.jpg', 'Corporate Finance', 'Manage corporate finances', 1, GETDATE(), GETDATE(), 'Learn the principles of corporate finance, including investment analysis and financial management.', 1, 3),
('Assets/Images/Subject/thumb44.jpg', 'Culinary Arts', 'Master the culinary arts', 1, GETDATE(), GETDATE(), 'Develop advanced culinary skills and techniques.', 0, 10),
('Assets/Images/Subject/thumb45.jpg', 'Fashion Design', 'Design your own fashion', 1, GETDATE(), GETDATE(), 'Learn about fashion design principles and how to create your own designs.', 1, 4),
('Assets/Images/Subject/thumb46.jpg', 'Biotechnology', 'Explore biotechnology', 1, GETDATE(), GETDATE(), 'Understand the applications of biotechnology in various fields.', 0, 10),
('Assets/Images/Subject/thumb47.jpg', 'Creative Problem Solving', 'Solve problems creatively', 1, GETDATE(), GETDATE(), 'Learn techniques for creative problem solving and innovative thinking.', 1, 5),
('Assets/Images/Subject/thumb48.jpg', 'Fitness Training', 'Become a fitness trainer', 1, GETDATE(), GETDATE(), 'Gain the knowledge and skills to become a certified fitness trainer.', 0, 2),
('Assets/Images/Subject/thumb49.jpg', 'Mechanical Engineering', 'Understand mechanical engineering', 1, GETDATE(), GETDATE(), 'Learn the fundamentals of mechanical engineering and its applications in various industries.', 1, 10),
('Assets/Images/Subject/thumb50.jpg', 'Cognitive Science', 'Explore cognitive science', 1, GETDATE(), GETDATE(), 'Study the interdisciplinary field of cognitive science, including psychology, neuroscience, and artificial intelligence.', 0, 6);

-- Sample data for [dbo].[PricePackage]
INSERT INTO [dbo].[PricePackage] 
([name], [duration], [list_price], [sale_price], [subject_id], [status], [created_at], [updated_at]) 
VALUES
-- Subject 1
('Basic Plan', 30, 50.00, 25.00, 1, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 120.00, 85.00, 1, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 220.00, 180.00, 1, 1, GETDATE(), GETDATE()),
-- Subject 2
('Basic Plan', 30, 55.00, 30.00, 2, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 125.00, 90.00, 2, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 230.00, 190.00, 2, 1, GETDATE(), GETDATE()),
-- Subject 3
('Basic Plan', 30, 60.00, 35.00, 3, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 130.00, 95.00, 3, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 240.00, 200.00, 3, 1, GETDATE(), GETDATE()),
-- Subject 4
('Basic Plan', 30, 65.00, 40.00, 4, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 135.00, 100.00, 4, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 250.00, 210.00, 4, 1, GETDATE(), GETDATE()),
-- Subject 5
('Basic Plan', 30, 70.00, 45.00, 5, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 140.00, 105.00, 5, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 260.00, 220.00, 5, 1, GETDATE(), GETDATE()),
-- Subject 6
('Basic Plan', 30, 75.00, 50.00, 6, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 145.00, 110.00, 6, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 270.00, 230.00, 6, 1, GETDATE(), GETDATE()),
-- Subject 7
('Basic Plan', 30, 80.00, 55.00, 7, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 150.00, 115.00, 7, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 280.00, 240.00, 7, 1, GETDATE(), GETDATE()),
-- Subject 8
('Basic Plan', 30, 85.00, 60.00, 8, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 155.00, 120.00, 8, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 290.00, 250.00, 8, 1, GETDATE(), GETDATE()),
-- Subject 9
('Basic Plan', 30, 90.00, 65.00, 9, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 160.00, 125.00, 9, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 300.00, 260.00, 9, 1, GETDATE(), GETDATE()),
-- Subject 10
('Basic Plan', 30, 95.00, 70.00, 10, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 165.00, 130.00, 10, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 310.00, 270.00, 10, 1, GETDATE(), GETDATE()),
-- Subject 11
('Basic Plan', 30, 100.00, 75.00, 11, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 170.00, 135.00, 11, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 320.00, 280.00, 11, 1, GETDATE(), GETDATE()),
-- Subject 12
('Basic Plan', 30, 105.00, 80.00, 12, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 175.00, 140.00, 12, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 330.00, 290.00, 12, 1, GETDATE(), GETDATE()),
-- Subject 13
('Basic Plan', 30, 110.00, 85.00, 13, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 180.00, 145.00, 13, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 340.00, 300.00, 13, 1, GETDATE(), GETDATE()),
-- Subject 14
('Basic Plan', 30, 115.00, 90.00, 14, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 185.00, 150.00, 14, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 350.00, 310.00, 14, 1, GETDATE(), GETDATE()),
-- Subject 15
('Basic Plan', 30, 120.00, 95.00, 15, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 190.00, 155.00, 15, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 360.00, 320.00, 15, 1, GETDATE(), GETDATE()),
-- Subject 16
('Basic Plan', 30, 125.00, 100.00, 16, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 195.00, 160.00, 16, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 370.00, 330.00, 16, 1, GETDATE(), GETDATE()),
-- Subject 17
('Basic Plan', 30, 130.00, 105.00, 17, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 200.00, 165.00, 17, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 380.00, 340.00, 17, 1, GETDATE(), GETDATE()),
-- Subject 18
('Basic Plan', 30, 135.00, 110.00, 18, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 205.00, 170.00, 18, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 390.00, 350.00, 18, 1, GETDATE(), GETDATE()),
-- Subject 19
('Basic Plan', 30, 140.00, 115.00, 19, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 210.00, 175.00, 19, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 400.00, 360.00, 19, 1, GETDATE(), GETDATE()),
-- Subject 20
('Basic Plan', 30, 145.00, 120.00, 20, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 215.00, 180.00, 20, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 410.00, 370.00, 20, 1, GETDATE(), GETDATE()),
-- Subject 21
('Basic Plan', 30, 150.00, 125.00, 21, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 220.00, 185.00, 21, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 420.00, 380.00, 21, 1, GETDATE(), GETDATE()),
-- Subject 22
('Basic Plan', 30, 155.00, 130.00, 22, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 225.00, 190.00, 22, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 430.00, 390.00, 22, 1, GETDATE(), GETDATE()),
-- Subject 23
('Basic Plan', 30, 160.00, 135.00, 23, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 230.00, 195.00, 23, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 440.00, 400.00, 23, 1, GETDATE(), GETDATE()),
-- Subject 24
('Basic Plan', 30, 165.00, 140.00, 24, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 235.00, 200.00, 24, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 450.00, 410.00, 24, 1, GETDATE(), GETDATE()),
-- Subject 25
('Basic Plan', 30, 170.00, 145.00, 25, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 240.00, 205.00, 25, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 460.00, 420.00, 25, 1, GETDATE(), GETDATE()),
-- Subject 26
('Basic Plan', 30, 175.00, 150.00, 26, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 245.00, 210.00, 26, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 470.00, 430.00, 26, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 610.00, 510.00, 26, 1, GETDATE(), GETDATE()),
-- Subject 27
('Basic Plan', 30, 180.00, 155.00, 27, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 250.00, 215.00, 27, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 480.00, 440.00, 27, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 620.00, 520.00, 27, 1, GETDATE(), GETDATE()),
-- Subject 28
('Basic Plan', 30, 185.00, 160.00, 28, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 255.00, 220.00, 28, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 490.00, 450.00, 28, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 630.00, 530.00, 28, 1, GETDATE(), GETDATE()),
-- Subject 29
('Basic Plan', 30, 190.00, 165.00, 29, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 260.00, 225.00, 29, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 500.00, 460.00, 29, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 640.00, 540.00, 29, 1, GETDATE(), GETDATE()),
-- Subject 30
('Basic Plan', 30, 195.00, 170.00, 30, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 265.00, 230.00, 30, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 510.00, 470.00, 30, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 650.00, 550.00, 30, 1, GETDATE(), GETDATE()),
-- Subject 31
('Basic Plan', 30, 200.00, 175.00, 31, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 270.00, 235.00, 31, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 520.00, 480.00, 31, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 660.00, 560.00, 31, 1, GETDATE(), GETDATE()),
-- Subject 32
('Basic Plan', 30, 205.00, 180.00, 32, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 275.00, 240.00, 32, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 530.00, 490.00, 32, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 670.00, 570.00, 32, 1, GETDATE(), GETDATE()),
-- Subject 33
('Basic Plan', 30, 210.00, 185.00, 33, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 280.00, 245.00, 33, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 540.00, 500.00, 33, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 680.00, 580.00, 33, 1, GETDATE(), GETDATE()),
-- Subject 34
('Basic Plan', 30, 215.00, 190.00, 34, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 285.00, 250.00, 34, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 550.00, 510.00, 34, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 690.00, 590.00, 34, 1, GETDATE(), GETDATE()),
-- Subject 35
('Basic Plan', 30, 220.00, 195.00, 35, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 290.00, 255.00, 35, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 560.00, 520.00, 35, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 700.00, 600.00, 35, 1, GETDATE(), GETDATE()),
-- Subject 36
('Basic Plan', 30, 225.00, 200.00, 36, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 295.00, 260.00, 36, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 570.00, 530.00, 36, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 710.00, 610.00, 36, 1, GETDATE(), GETDATE()),
-- Subject 37
('Basic Plan', 30, 230.00, 205.00, 37, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 300.00, 265.00, 37, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 580.00, 540.00, 37, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 720.00, 620.00, 37, 1, GETDATE(), GETDATE()),
-- Subject 38
('Basic Plan', 30, 235.00, 210.00, 38, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 305.00, 270.00, 38, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 590.00, 550.00, 38, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 730.00, 630.00, 38, 1, GETDATE(), GETDATE()),
-- Subject 39
('Basic Plan', 30, 240.00, 215.00, 39, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 310.00, 275.00, 39, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 600.00, 560.00, 39, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 740.00, 640.00, 39, 1, GETDATE(), GETDATE()),
-- Subject 40
('Basic Plan', 30, 245.00, 220.00, 40, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 315.00, 280.00, 40, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 610.00, 570.00, 40, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 750.00, 650.00, 40, 1, GETDATE(), GETDATE()),
-- Subject 41
('Basic Plan', 30, 250.00, 225.00, 41, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 320.00, 285.00, 41, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 620.00, 580.00, 41, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 760.00, 660.00, 41, 1, GETDATE(), GETDATE()),
-- Subject 42
('Basic Plan', 30, 255.00, 230.00, 42, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 325.00, 290.00, 42, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 630.00, 590.00, 42, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 770.00, 670.00, 42, 1, GETDATE(), GETDATE()),
-- Subject 43
('Basic Plan', 30, 260.00, 235.00, 43, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 330.00, 295.00, 43, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 640.00, 600.00, 43, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 780.00, 680.00, 43, 1, GETDATE(), GETDATE()),
-- Subject 44
('Basic Plan', 30, 265.00, 240.00, 44, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 335.00, 300.00, 44, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 650.00, 610.00, 44, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 790.00, 690.00, 44, 1, GETDATE(), GETDATE()),
-- Subject 45
('Basic Plan', 30, 270.00, 245.00, 45, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 340.00, 305.00, 45, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 660.00, 620.00, 45, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 800.00, 700.00, 45, 1, GETDATE(), GETDATE()),
-- Subject 46
('Basic Plan', 30, 275.00, 250.00, 46, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 345.00, 310.00, 46, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 670.00, 630.00, 46, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 810.00, 710.00, 46, 1, GETDATE(), GETDATE()),
-- Subject 47
('Basic Plan', 30, 280.00, 255.00, 47, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 350.00, 315.00, 47, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 680.00, 640.00, 47, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 820.00, 720.00, 47, 1, GETDATE(), GETDATE()),
-- Subject 48
('Basic Plan', 30, 285.00, 260.00, 48, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 355.00, 320.00, 48, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 690.00, 650.00, 48, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 830.00, 730.00, 48, 1, GETDATE(), GETDATE()),
-- Subject 49
('Basic Plan', 30, 290.00, 265.00, 49, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 360.00, 325.00, 49, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 700.00, 660.00, 49, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 840.00, 740.00, 49, 1, GETDATE(), GETDATE()),
-- Subject 50
('Basic Plan', 30, 295.00, 270.00, 50, 1, GETDATE(), GETDATE()),
('Standard Plan', 90, 365.00, 330.00, 50, 1, GETDATE(), GETDATE()),
('Premium Plan', 180, 710.00, 670.00, 50, 1, GETDATE(), GETDATE()),
('Advanced Plan', 365, 850.00, 750.00, 50, 1, GETDATE(), GETDATE());


-- Sample data for [dbo].[Registration]
INSERT INTO [dbo].[Registration] ([list_price], [sale_price], [user_id], [status], [valid_from], [valid_to], [notes], [package_id]) VALUES
(50.00, 25.00, 1, 0, GETDATE(), DATEADD(MONTH, 1, GETDATE()), 'Note 1', 1),
(100.00, 75.00, 1, 1, GETDATE(), DATEADD(MONTH, 3, GETDATE()), 'Note 2', 2),
(200.00, 150.00, 3, 1, GETDATE(), DATEADD(MONTH, 6, GETDATE()), 'Note 3', 3),
(50.00, 25.00, 5, 0, GETDATE(), DATEADD(MONTH, 1, GETDATE()), 'Note 4', 4),
(100.00, 75.00, 5, 1, GETDATE(), DATEADD(MONTH, 3, GETDATE()), 'Note 5', 5);

-- Sample data for [dbo].[Slider]
INSERT INTO [dbo].[Slider] ( [title], [image], [back_link], [status], [note]) VALUES
( 'GitHub', 'Assets/Images/Slider/github.jpg', 'https://github.com', 1, 'Promote GitHub'),
( 'Docker', 'Assets/Images/Slider/docker.jpg', 'https://docker.com', 1, 'Promote Docker'),
( 'GitLab', 'Assets/Images/Slider/gitlab.jpg', 'https://gitlab.com', 1, 'Promote GitLab'),
( 'Kubernetes', 'Assets/Images/Slider/kubernetes.jpg', 'https://kubernetes.io', 1, 'Promote Kubernetes'),
( 'Jenkins', 'Assets/Images/Slider/jenkins.jpg', 'https://jenkins.io', 1, 'Promote Jenkins'),
( 'Travis CI', 'Assets/Images/Slider/travis.jpg', 'https://travis-ci.org', 1, 'Promote Travis CI'),
( 'CircleCI', 'Assets/Images/Slider/circleci.jpg', 'https://circleci.com', 1, 'Promote CircleCI'),
( 'Bitbucket', 'Assets/Images/Slider/bitbucket.jpg', 'https://bitbucket.org', 1, 'Promote Bitbucket'),
( 'Azure DevOps', 'Assets/Images/Slider/azure_devops.jpg', 'https://dev.azure.com', 1, 'Promote Azure DevOps'),
( 'AWS', 'Assets/Images/Slider/aws.jpg', 'https://aws.amazon.com', 1, 'Promote AWS');