var $app = {};
(function () {
    if (typeof require === 'undefined') {
        return;
    }
    // 配置依赖
    require.config({
        baseUrl: '/haq-bms/lib',
        paths: {
            'jquery': 'jquery/jquery-1.12.0.min',
            'doT': 'doT/doT.min',
            'store': 'store/store.min',
            'bootstrap': 'bootstrap/js/bootstrap.min',
            'app': '/haq-bms/js/app',
            'blockUI': 'jquery/jquery.blockUI',
            'jQueryForm': 'jquery/jquery.form',
            'messenger': 'hubspot-messenger/js/messenger.min'
        },
        shim: {
            'bootstrap': {
                deps: ['jquery']
            },
            'app': {
                deps: ['jquery', 'doT', 'store', 'blockUI', 'bootstrap', 'messenger']
            },
            'messenger': {
                deps: ['jquery']
            }
        }
    });

    $app.toJSON = function (data) {
        if (!data)  return {};
        try {
            if (JSON && JSON.toJSON) {
                return JSON.toJSON(data);
            } else {
                return eval('(' + data + ')');
            }
        } catch (e) {
            return {}
        }
    };

    $app.ajax = function (url, param, callback) {
        //var msgWrapper = $('#msg-wrapper');
        $.ajax({
            url: url,
            data: param,
            type: 'post',
            complete: function (jqXHR, textStatus) {
                var json;
                switch (textStatus) {
                    case 'success':
                        if (jqXHR && jqXHR.responseText)
                            json = $app.toJSON(jqXHR.responseText);
                        break;
                    case 'timeout':
                        json = {code: 444, msg: '请求超时，请稍后重试'};
                        break;
                    default:
                        json = {code: 444, msg: '加载失败，请稍后再试'};
                        break;
                }

                if ($.isFunction(callback)) {
                    callback.call(null, json);
                }
            }
        })
    };
    /**
     * 主函数
     */
   require(['jquery', 'jQueryForm'], function ($) {
        $(function () {
            // 登录和忘记密码切换
            $('.login-helpers a').click(function () {
                var $t = $(this);
                $('.visible').removeClass('visible animated fadeInUp');
                $($t.data('target')).addClass('visible animated fadeInUp');
            });

            // 登录事件
            $('#btn').click(function(){
                mySubmit();
            });

            document.onkeydown=function(event){
                var e = event ? event :(window.event ? window.event : null);
                if(e.keyCode==13){
                    mySubmit();
                }
            };

            function mySubmit(){
                var password=$('#exampleInputPassword1').val(),
                    email=$('#exampleInputEmail2').val();
                $app.ajax('/haq-bms/user/login',{email:email,password:password},function(data){
                    if (data.code==200) {
                        location.href = "/haq-bms/main";
                        return;
                    }else{
                        $("#msg-wrapper").show();
                        $("#msg-wrapper p").html(''+data.msg+'');
                    }
                });
                return false;
            }
        })

    })
})();