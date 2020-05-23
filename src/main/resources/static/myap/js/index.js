var url = "http://39.106.211.84:12453";
var options = {
    list:".pl archive-thumb",//列表标识
    currentPage:1,//初始页（选传，默认1）
    pageSize:5,//每页列表数
    listTotal:31,//列表总数（选传），不传为list总数
    callback:function(currentPage){//翻页回调（可填，可做ajax请求）,不传为纯html切换
        //loadData(ajaxDemo(currentPage))
    }
}
$(document).ready(function () {
    changeBattle("home-fluid");

    $(".user-login li").on("click",function (e) {
        e.stopPropagation();
    })
    autoLogin();
	 $(document).click(function (event) {
        $('.search-expand').hide();
        $('.toggle-search').removeClass('active');
    });
    $('.toggle-search ,.search-expand').click(function (event) {
        event.stopPropagation();
    });
    $('.toggle-search').click(function () {
        $('.toggle-search').toggleClass('active');
        $('.search-expand').fadeToggle(250);
        setTimeout(function () {
            $('.search-expand input').focus();
        }, 300);
    });
    /*  fiexd menu
    /* ------------------------------------ */
    if ($(".nav-fixed").length > 0) {
        var navTop = $('.nav-fixed').offset().top;
        $(window).scroll(function () {
            if ($(window).scrollTop() > navTop && $(window).width() > 768) {
                $('.nav-fixed').addClass('fixed');
            } else {
                $('.nav-fixed').removeClass('fixed');
            }
        });
    }
    $.ajax({
        type: "Get",
        url: url+"/word/query/byWordLevel",
        dataType: "Json",
        data: {wordLevel:0},
        success: function(result) {
            var wordLevelBox = document.getElementById("wordLevel");
            var dataNumber = result.data.length;
            if(dataNumber>10){
                dataNumber = 10;
            }
            for(var i = 0;i<dataNumber;i++){
                var newLi = document.createElement("li");
                var spanDate = result.data[i].publishDate.substring(5,10);
                var spanClass = "black";
                if(spanDate==getNowFormatDate()){
                    spanClass = "red";
                }
                newLi.innerHTML = "<span class=\""+spanClass+"\">"+spanDate+"</span>\n" +
                    "                                    <a href=\"#\" title="+result.data[i].title.replace(/ /g,'&#32;')+" id = "+result.data[i].id+"-"+result.data[i].wordLevel+" onclick=\"javascript:createWordUlByParentId(this.title+'-'+this.id)\">" +
                    "                                        <i class=\"fa fa-angle-right\"></i>"+result.data[i].title+"" +
                    "                                    </a>";
                wordLevelBox.appendChild(newLi);
            }
        }
    });
    $.ajax({
        type: "Get",
        url: url+"/word/query/byRandom",
        dataType: "Json",
        data: {number:10},
        success: function(result) {
            var dataNumber = result.data.length;
            if(!dataNumber<=0){
                for(var i = 0;i<dataNumber;i++){
                    var newLi = document.createElement("li");
                    var spanDate = result.data[i].publishDate.substring(5,10);
                    var spanClass = "black";
                    if(spanDate==getNowFormatDate()){
                        spanClass = "red";
                    }
                    newLi.innerHTML = "<span class=\""+spanClass+"\">"+spanDate+"</span>\n" +
                        "                                    <a href=\"#\" title="+result.data[i].title.replace(/ /g,'&#32;')+" id = "+result.data[i].id+"-"+result.data[i].wordLevel+" onclick=\"javascript:publishDateContinue(this.title+'-'+this.id)\">" +
                        "                                        <i class=\"fa fa-angle-right\"></i>"+result.data[i].title+"" +
                        "                                    </a>";
                    $(".news-list.randomWordList").prepend(newLi);
                }
            }
        }
    });
    $.ajax({
        type: "Get",
        url: url+"/word/find/allOrderByPublishDate",
        dataType: "Json",
        success: function(result) {
            var dataNumber = result.data.length;
            if(dataNumber>10){
                dataNumber = 10;
            }
            for(var i = 0;i<dataNumber;i++){
                var newLi = document.createElement("li");
                var spanDate = result.data[i].publishDate.substring(5,10);
                var spanClass = "black";
                if(spanDate==getNowFormatDate()){
                    spanClass = "red";
                }
                newLi.innerHTML = "<span class=\""+spanClass+"\">"+spanDate+"</span>\n" +
                    "                                    <a href=\"#\" title="+result.data[i].title.replace(/ /g,'&#32;')+" id = "+result.data[i].id+"-"+result.data[i].wordLevel+" onclick=\"javascript:publishDateContinue(this.title+'-'+this.id)\">" +
                    "                                        <i class=\"fa fa-angle-right\"></i>"+result.data[i].title+"" +
                    "                                    </a>";
                $(".news-list.publishDateList").prepend(newLi);
            }
        }
    });
});
function getNowFormatDate() {
    var date = new Date();
    //var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = month + "-" + strDate;
    return currentdate;
}
var Authorization;
var role;
var user;
function autoLogin() {
    if ($.cookie("rememberme") == "true") {
        var username = $.cookie("username");
        var authorization = $.cookie("Authorization");
        $.ajax({
            type: "Post",
            url: url+"/user/getUserDetailByToken",
            dataType: "Json",
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", authorization);

            },
            success: function(result) {
               if(result.status==200&&result.data.username==username){
                   Authorization = authorization;
                   role = result.data.authorities[0].authority;
                   user = username;
                   resetMenu(username);
               }else {
                   alert("登录信息过期，请重新登陆");
               }
                $(".update").show();
            },
            error:function (message) {
                console.log(message);
            }
        })
    }else{
        $("#rememberme").prop("checked", false);
    }
}
function userLogin(){
    var username = $("#username").val();
    var password = $("#password").val();
    if(username!=""&&password!="") {
        $.ajax({
            type: "Post",
            url: url+"/user/form",
            dataType: "Json",
            data: {username:username,password:password},
            success: function(data) {
				if(data.status != 200){
					alert(data.message);
				}else {
                    Authorization = data.data[0];
                    role = data.data[1][0].authority;
                    user = username;
					if ($("#rememberme").prop("checked")==true) {
						$.cookie("rememberme", "true", {expires: 7});
						$.cookie("username", username, {expires: 7});
						$.cookie("Authorization", Authorization, {expires: 7});
					} else {
						$.cookie("rememberme", "false", {expire: -1});
						$.cookie("username", "", {expires: -1});
						$.cookie("Authorization", "", {expires: -1});
					}
					resetMenu(username);
				}
                $(".update").show();
            },
            error:function (message) {
                console.log(message);
            }
        })
    }
}
function userRegister() {
    var username = $("#username-reg").val();
    var password = $("#password-reg").val();
    var repeat = $("#password-repeat").val();
    if(password!=repeat){
        alert("重复密码与密码不一致！");
        return;
    }
    $.ajax({
        type: "Post",
        url: url+"/customer/addOrUpdate",
        dataType: "Json",
        data: {username:username,password:password},
        success: function(data) {
            alert("注册成功，即将自动登录")
            $("#username").val(username);
            $("#password").val(password);
            userLogin();
        },
        error:function (message) {
            console.log(message);
        }
    })
}
/* Authorization ：token */
function resetMenu(username){
    var usernav = document.getElementById("user-nav");
    usernav.innerHTML ="<li class=\"dropdown open\" id=\"menu-messages\"><a href=\"#\" data-toggle=\"dropdown\" data-target=\"#menu-messages\" class=\"dropdown-toggle\"><i class=\"fa fa-fw fa-user\"></i> <span id = \"user-span\" class=\"text\">"+username+"</span><b class=\"caret\"></b></a>\n" +
        "<ul class=\"dropdown-menu user-dashboard\">\n" +
        "<li><a onclick=\"javascript:myWord()\" href=\"#\"><i class=\"fa fa-book  fa-fw\"></i>我的文档</a></li>\n" +
        "<li><a onclick=\"javascript:handleWord()\" href=\"#\"><i class=\"fa fa-edit fa-fw\"></i>文档操作</a></li>\n" +
        "<li><a onclick=\"javascript:userSetting()\" href=\"#\"><i class=\"fa fa-cogs fa-fw\"></i>个人资料</a></li>\n" +
        "</ul>\n"+
        "</li>"+
        "<li class=\"user-btn\"><a onclick=\"javascript:signOut()\" href=\"\" data-simpletooltip-position=\"bottom\" title=\"登出\" rel=\"nofollow\"><i class=\"fa fa-sign-out fa-fw\"></i><span class=\"text\">登出</span></a></li>";
}
function myWord() {
    if(roleControl()==true) {
        changeBattle("word-row-fluid");
        document.getElementById("archive-head-h1").innerHTML = user;
        $.ajax({
            type: "Get",
            url: url+"/word/query/byAuthor",
            dataType: "Json",
            data: {author: user},
            success: function (result) {
                var newdata = postCollection(result.data);
                options.listTotal = result.data.length;
                options.callback = function (currentPage) {
                    postinit(newdata, currentPage - 1);
                };
                postinit(newdata, 0);
                $("#word-posts-ul").paging(options);
                $(".posts-ul-div").show("fast");
            },
            error: function (message) {
                console.log(message);
            }
        })
    }
}
function handleWord() {
    alert("文档操作 正在制作");
}
function userSetting() {
    alert("用户设置 正在制作");
}
function signOut() {
    $.cookie("rememberme", "false", {expire: -1});
    $.cookie("username", "", {expires: -1});
    $.cookie("Authorization", "", {expires: -1});
    changeBattle();
}
function changeBattle(changObj) {
    //self.location = document.referrer;
    var allgui =new Array("updateWord","addWord","word-row-fluid","home-fluid","posts-ul-div","single-post-div","post-add-div","nothing-post");
    for(var i=0;i<allgui.length;i++){
        if(allgui[i]!=changObj) {
            $("." + allgui[i] + "").hide("normal");
        }
    }
    $("."+changObj+"").show("normal");
}
function publishDateContinue(TitleAndIdAndWordLevel) {
    var array = TitleAndIdAndWordLevel.split("-");
    if(array[2]<3){
        createWordUlByParentId(TitleAndIdAndWordLevel);
    }else {
        createSinglePost(TitleAndIdAndWordLevel);
    }
}
function roleControl(){
    if(Authorization==undefined){
        alert("只有登录后才可新增/修改文档");
        return false;
    }else if(!(role=="User"||role=="Admin")){
        alert("您的权限不足，请联系管理员提升权限，当前权限："+role);
        return false;
    }else {
        return true;
    }
}
function addWordContinue(TitleAndIdAndWordLevelAndParentId) {
    var array = TitleAndIdAndWordLevelAndParentId.split("-");
    if(roleControl()==true){
        $("#add-parent").val(array[0]);
        $(".post-add").attr("id","post-add-"+array[1]+'-'+array[2]);
        $("#add-author").val(user);
        if(array[0]=="文档目录"){
            $("#add-wordlevel").val(Number(array[2]));
        }else{
            $("#add-wordlevel").val(Number(array[2])+1);
        }
        $(".chose").attr("onclick","javascript:addWord()");
        $("#add-title").val("");
        $("#add-area").val("");
        changeBattle("word-row-fluid");
        $(".post-add-div").show("fast");
    }
}
function updateWordContinue(TitleAndIdAndWordLevelAndParentId) {
    var array = TitleAndIdAndWordLevelAndParentId.split("-");
    if(roleControl()==true){
        $("#add-parent").val($("#archive-head-h1").innerText);
        $(".post-add").attr("id","post-update-"+array[1]+'-'+array[2]+'-'+array[3]);
        $("#add-author").val(user);
        $("#add-wordlevel").val(Number(array[2]));
        $("#add-title").val($("#post-"+array[1]+" a")[0].innerHTML);
        $("#add-area").val($("#post-"+array[1]+" p")[1].innerHTML);
        $(".chose").attr("onclick","javascript:updateWord()");
        changeBattle("word-row-fluid");
        $(".post-add-div").show("fast");
    }
}
function addWord() {
    var array = $(".post-add").attr("id").split("-");
    var sendDate = new Array(
        0,
        $("#add-title").val(),
        $("#add-area").val(),
        $("#add-author").val(),
        Number(array[2]),
        $("#add-wordlevel").val()
    );
    for(var i = 0;i<sendDate.length;i++){
        if(sendDate[i]==undefined){
            alert("信息不完整，禁止添加")
            return;
        }
    }
        $.ajax({
            type: "Post",
            url: url+"/word/addOrUpdate",
            dataType: "Json",
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", Authorization);
            },
            data: {
                Id:sendDate[0],
                title:sendDate[1],
                textArea:sendDate[2],
                author:sendDate[3],
                parentId:sendDate[4],
                wordLevel:sendDate[5]
            },
            success:function(result) {
                alert("已成功添加文档");
                self.location = document.referrer;
            },
            error:function (message) {
                console.log(message);
            }
        })
}
function updateWord() {
    var array = $(".post-add").attr("id").split("-");
    var sendDate = new Array(
        Number(array[2]),
        $("#add-title").val(),
        $("#add-area").val(),
        $("#add-author").val(),
        Number(array[4]),
        $("#add-wordlevel").val()
    );

    for(var i = 0;i<sendDate.length;i++){
        if(sendDate[i]==undefined||sendDate[i]==null){
            alert("信息不完整，禁止修改")
            return;
        }
    }
    $.ajax({
        type: "Post",
        url: url+"/word/addOrUpdate",
        dataType: "Json",
        headers: {
            'Authorization': Authorization,
        },
        data: {
            id:sendDate[0],
            title:sendDate[1],
            textArea:sendDate[2],
            author:sendDate[3],
            parentId:sendDate[4],
            wordLevel:sendDate[5]
        },
        success:function(result) {
            alert("已成功添加文档");
            self.location = document.referrer;
        },
        error:function (message) {
            console.log(message);
        }
    })
}
function deleteWord(TitleAndIdAndWordLevelAndParentId) {
    var array = TitleAndIdAndWordLevelAndParentId.split("-");
    $("post-"+array[1]).remove();
    if(roleControl()==true){
        $.ajax({
            type: "Get",
            url: url+"/word/delete/byId",
            dataType: "Json",
            headers: {
                'Authorization': Authorization,
            },
            data: {
                id:Number(array[1]),
            },
            success:function(result) {
                alert("已成功删除文档");
                self.location = document.referrer;
            },
            error:function (message) {
                console.log(message);
            }
        })
    }
}
function searchPost() {
    changeBattle("word-row-fluid");
    document.getElementById("archive-head-h1").innerHTML="搜索结果";
    $.ajax({
        type: "Get",
        url: url+"/word/query/byTitle",
        dataType: "Json",
        data: {title:$(".search").val()},
        success: function (result) {
            var newdata = postCollection(result.data);
            options.listTotal=result.data.length;
            options.callback=function(currentPage){
                postinit(newdata,currentPage-1);
            };
            postinit(newdata,0);
            $("#word-posts-ul").paging(options);
            $(".posts-ul-div").show("fast");
        },
        error:function (message) {
            console.log(message);
        }
    })
}
function createSinglePost(TitleAndIdAndWordLevel) {
    changeBattle("word-row-fluid");
    var array = TitleAndIdAndWordLevel.split("-");
    $.ajax({
        type: "Get",
        url: url+"/word/query/byId",
        dataType: "Json",
        data: {id:array[1]},
        success:function(result){
            if(result.data!=null) {
                $(".single-post-div").empty();
                var text = result.data.textArea.replace(/\n/g, "<br/>");
                var newArticle = document.createElement("article");
                newArticle.setAttribute("id", "post-" + result.data.id);
                newArticle.setAttribute("class", "widget-content single-post");
                newArticle.innerHTML = "" +
                    "<header class=\"post-header\">" +
                    "<h1 class=\"post-title\">" + result.data.title + "</h1>" +
                    "<div class=\"clear\"></div>" +
                    "<p class=\"post-meta\">" +
                    "<span><i class=\"fa fa-user fa-fw\"></i><a href=\"#\" title=\"作者\">" + result.data.author + "</a></span>" +
                    "<span><i class=\"fa fa-clock-o fa-fw\"></i>" + result.data.publishDate.substring(0, 10) + "</span>" +
                    //"<span><i class=\"fa fa-eye fa-fw\"></i>未来的点击数</span>\n" +
                    "<span><i class=\"fa fa-folder-open fa-fw\"></i>文档等级：" + result.data.wordLevel + "</span>" +
                    "</p>" +
                    "<div class=\"clear\"></div>" +
                    "</header>" +
                    "<div class=\"entry\">" +
                    "<p>" + text + "</p>" +
                    "</div>"
                $(".single-post-div").prepend(newArticle);
                $(".single-post-div").show("fast");
            }else{
                alert("无匹配项")
            }
        },
        error:function (message) {
            console.log(message);
        }
    })
}
function createWordUlByParentId(TitleAndIdAndWordLevel){
    changeBattle("word-row-fluid");
    var array = TitleAndIdAndWordLevel.split("-");
    document.getElementById("archive-head-h1").innerHTML=array[0];
    $(".addWord").attr("title",array[0]);
    $(".addWord").attr("id",array[1]+'-'+array[2]);
    $(".addWord").show("normal");
    $.ajax({
        type: "Get",
        url: url+"/word/query/byParentId",
        dataType: "Json",
        data: {parentId:array[1]},
        success: function (result) {
            var newdata = postCollection(result.data);
            options.listTotal=result.data.length;
            options.callback=function(currentPage){
                postinit(newdata,currentPage-1);
            };
            postinit(newdata,0);
            $("#word-posts-ul").paging(options);
            $(".posts-ul-div").show("fast");
        },
        error:function (message) {
            console.log(message);
        }
    })
}
function postinit(data,pageNum) {
    $("#word-posts-ul").empty();
    if(data.length>0)
        for (var i = 0;i<data[pageNum].length;i++){
            $("#word-posts-ul").prepend(data[pageNum][i]);
        }
}

function createWordUlByWordLevel(WordLevel){
    changeBattle("word-row-fluid");
    if(WordLevel==0){
        document.getElementById("archive-head-h1").innerHTML="文档目录";
        $(".addWord").attr("title","文档目录");
        $(".addWord").attr("id","0-0");
        $(".addWord").show("normal");
    }else{
        document.getElementById("archive-head-h1").innerHTML=WordLevel+"级文档";
    }
    $.ajax({
        type: "Get",
        url: url+"/word/query/byWordLevel",
        dataType: "Json",
        data: {wordLevel:WordLevel},
        success: function (result) {
            $(".nothing-post").hide();
            var newdata = postCollection(result.data);
            options.listTotal=result.data.length;
            options.callback=function(currentPage){
                postinit(newdata,currentPage-1);
            };
            postinit(newdata,0);
            $("#word-posts-ul").paging(options);
            $(".posts-ul-div").show("fast");
        },
        error:function (message) {
            console.log(message);
        }
    })
}

function postCollection(data) {
    var next,nextFunction;
    var postList = [],arr=[];
    for(var i=0; i<data.length;i++) {
        if(data[i].wordLevel<3){
            next = "查看更多";
            nextFunction = "createWordUlByParentId(this.title+'-'+this.id)";
        } else  {
            next = "阅读全文";
            nextFunction = "createSinglePost(this.title+'-'+this.id)";
        }
        var newLi = document.createElement("li");
        newLi.setAttribute("class","pl archive-thumb");
        newLi.setAttribute("id","post-"+data[i].id);
        newLi.innerHTML = "" +
            "<article>" +
            "<h2><a>" + data[i].title + "</a>" +
            "<a class=\"update\" title = "+data[i].title.replace(/ /g,'&#32;')+" id = "+data[i].id+'-'+data[i].wordLevel+'-'+data[i].parentId+" style='font-size: x-small;float: right;margin-right:5px;margin-left: 20px;text-decoration:underline' onclick=\"javascript:updateWordContinue(this.title+'-'+this.id)\" title=\"修改\">修改</a>" +
            "<a class=\"delete\" title = "+data[i].title.replace(/ /g,'&#32;')+" id = "+data[i].id+'-'+data[i].wordLevel+'-'+data[i].parentId+" style='font-size: x-small;float: right;margin-right:5px;margin-left: 20px;text-decoration:underline' onclick=\"javascript:deleteWord(this.title+'-'+this.id)\" title=\"删除\">删除</a></h2>" +
            "</h2>" +"<p class=\"more\">" +
            "<a title = "+data[i].title.replace(/ /g,'&#32;')+" class=\"more-link\" href=\"#\" id = "+data[i].id+"-"+data[i].wordLevel+" onclick=\"javascript:"+nextFunction+"\">"+next+"</a>" +
            "</p>"+
            "<p id = \"textArea\">"+data[i].textArea+"</p></a>" +
            //"<span><a class=\"more-link\" href=\"#\" onclick=\"javascript:createWordUl(this.value) value="+result.data[i].id+"\">更多</a></span>" +
            "<p class=\"post-meta\">" +
            "<span><i class=\"fa fa-user fa-fw\"></i><a href=\"#\" title=\"作者\">" + ""+data[i].author+"" + "</a></span>" +
            "<span><i class=\"fa fa-clock-o fa-fw\"></i>" + ""+data[i].publishDate.substring(0,10)+"" + "</span>" +
            "<span><i class=\"fa fa-folder-open fa-fw\"></i>文档等级："+data[i].wordLevel+"</span>"+
            //"<span><i class=\"fa fa-eye fa-fw\"></i>" + "未来的点击数" + "</span>" +
            "</p>" +
            "</article>";
        arr.push(newLi);
        if(i!=0&&(i+1)%options.pageSize==0){
            postList.push(arr);
            arr=[];
        }
    }
    if(arr.length>0)
        postList.push(arr);
    return postList;
}

(function ($) {

    $.fn.menumaker = function (options) {

        var cssmenu = $(this), settings = $.extend({
            format: "dropdown",
            sticky: false
        }, options);

        return this.each(function () {
            $(this).find("#menu-button").on('click', function () {
                $(this).toggleClass('menu-opened');
                var mainmenu = $(this).next('ul');
                if (mainmenu.hasClass('open')) {
                    mainmenu.hide().removeClass('open');
                } else {
                    mainmenu.show().addClass('open');
                    if (settings.format === "dropdown") {
                        mainmenu.find('ul').show();
                    }
                }
            });

            cssmenu.find('li ul').parent().addClass('has-sub');

            multiTg = function () {
                cssmenu.find(".has-sub").prepend('<span class="submenu-button"></span>');
                cssmenu.find('.submenu-button').on('click', function () {
                    $(this).toggleClass('submenu-opened');
                    if ($(this).siblings('ul').hasClass('open')) {
                        $(this).siblings('ul').removeClass('open').hide();
                    } else {
                        $(this).siblings('ul').addClass('open').show();
                    }
                });
            };

            if (settings.format === 'multitoggle') multiTg();
            else cssmenu.addClass('dropdown');

            if (settings.sticky === true) cssmenu.css('position', 'fixed');

            resizeFix = function () {
                if ($(window).width() > 768) {
                    cssmenu.find('ul').show();
                }

                if ($(window).width() <= 768) {
                    cssmenu.find('ul').hide().removeClass('open');
                }
            };
            resizeFix();
            return $(window).on('resize', resizeFix);

        });
    };
    $(document).ready(function () {
        $("#main-nav").menumaker({
            format: "multitoggle"
        });
    });

})(jQuery);
