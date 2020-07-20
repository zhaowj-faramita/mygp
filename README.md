# mygp
毕业设计
已上线于阿里云：http://39.106.211.84:12450/myap/

用户处理器
该处理器供提供5种接口：
1.	/customer/addOrUpdate：添加新的用户或者修改一个已有的用户
2.	/customer/deleteCustomer：通过id删除用户
3.	/customer/deleteCustomerByUsername：通过用户名删除用户
4.	/customer/find/All：查询所有用户
5.	/customer/query/ById：根据主键查询用户

登陆处理器
该处理器仅提供两种接口：
1.	/user/form：登入身份验证（JWT验证）
2.	/user/getUserDetailByToken：根据token（令牌）得到用户信息

文档处理器
该处理器提供共11种接口，且未来大概率会添加新的接口：
1.	/word/addOrUpdate：添加一个文档或者修改一个已有的文档
2.	/word/delete/byId：通过id删除题目
3.	/word/delete/byTitle：通过题目删除题目
4.	/word/find/all：查询所有文档
5.	/word/find/allOrderByPublishDate：查询所有文档，并通过上传时间排序
6.	/word/query/byId：根据主键查询文档
7.	/word/query/byAuthor：根据作者获取文档
8.	/word/query/byParentId：根据某文档id获取其从属文档
9.	/word/query/byWordLevel：根据文档级别获取文档
10.	/word/query/byRandom：随机获取文档
11.	/word/query/byTitle:根据文档标题获取文档
