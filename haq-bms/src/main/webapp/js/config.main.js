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
            'my97date': 'My97DatePicker/WdatePicker',
            'app': '/haq-bms/js/app',
            'blockUI': 'jquery/jquery.blockUI',
            'jQueryForm': 'jquery/jquery.form',
            'messenger': 'hubspot-messenger/js/messenger.min',
            'validation': 'jquery/jquery.validationEngine.min',
            'validation.lang': 'jquery/jquery.validationEngine-zh_CN',
            'ztree' : 'ztree/js/jquery.ztree.all-3.5',
            'echarts':'echarts/echarts.min',
            'china':'echarts/china'
        },
        waitSeconds: 0 ,
        shim: {
            'bootstrap': { deps: ['jquery'] },
            'app': {
                deps: ['jquery', 'doT', 'store', 'blockUI', 'bootstrap','messenger', 'validation']
            },
            'messenger': {  deps: ['jquery'] },
            'validation': { deps: ['jquery', 'validation.lang'] },
            'validation.lang': {  deps: ['jquery'] },
            'ztree' : {  deps: ['jquery'] }
        }
    });

    /**
     * 主函数
     */
    require(['jquery', 'app', 'jQueryForm','validation','my97date'], function ($, app) {
        // 初始化
        init();
        var $body = $('body'),
            $wrapper = $('#content-main');

        $body.on('click', '.sidebar-menu .has-sub > a', function () {
            var slideSpeed = 200;
            var sub = $(this).next();
            if (sub.is(":visible")) {
                $('.arrow', $(this)).removeClass("open");
                //$(this).children('.arrow').removeClass("open");
                $(this).parent().removeClass("open");
                sub.slideUp(slideSpeed);
            } else {
                $('.arrow', $(this)).addClass("open");
                $(this).parent().addClass("open");
                sub.slideDown(slideSpeed);
            }
        });

        $body.on('click', '#sidebar-collapse', function () {
            $('#sidebar').toggleClass('mini-menu');
            $('.page-right').toggleClass('m-left-50');
        });


        // 菜单点击事件
        $body.on('click', 'ul.sub li a.ls', function () {
            var $t = $(this);
            var url = $t.attr('href');
            var mContent = $('#content-main');

            $('ul.sub li').removeClass('current');
            $t.parent().addClass('current');

            $('#menu-text').html($t.find('span').text());

            app.block();
            mContent.load(url, {time: new Date().getTime()}, function (responseText, textStatus) {
                var html;
                app.unblock();
                switch (textStatus) {
                    case 'timeout':
                        html = app.buildMsg('请求超时，请稍后再试');
                        break;
                    case 'error':
                        html = app.buildMsg('加载失败，请稍后再试');
                        break;
                    case 'success':
                        // 加载页面的模块
                        app.loadModule();
                        break;
                }
                if (html)
                    mContent.html('<div class="col-md-12">' + html + '</div>');
            });
            return false;
        });

        // 面板一级按钮点击事件，即面板攻击栏的ICON点击事件
        $wrapper.on('click', '.tools a', function () {
            var $t = $(this),
                $panel = $t.parents('.box'),
                url = $t.data('url'),
                param = $t.data('param'),
                modal,
                text,
                template = $t.data('template-id');
            if ($t.hasClass('reload')) {
                // 刷新
                app.loadModule($panel);
            } else if ($t.hasClass('collapse')) {
                // 伸开合并
                var $icon = $t.children('.fa');
                if ($icon.hasClass('fa-chevron-down')) {
                    $icon.addClass('fa-chevron-up').removeClass('fa-chevron-down');
                } else {
                    $icon.addClass('fa-chevron-down').removeClass('fa-chevron-up');
                }
                $panel.children('.box-body').fadeToggle();
            }

            if (!template)  return;
            // 获取模板内容
            text = $(template).html();
            // 删除
            if ($t.hasClass('remove')) {
                param = {};
                param.url = $t.data('del-url');
                var $tr,
                    list = [];
                $panel.find('tr.active').each(function () {
                    $tr = $(this);
                    list.push({name: $tr.data('name'), id: $tr.data('id')})
                });
                if (list.length === 0) {
                    $('#myDelete').fadeIn();
                    $('.myDelete').click(function(){
                        $('#myDelete').fadeOut();
                    });
                    return;
                }
                param.list = list;
                modal = $(app.template(text, param));
                buildModal(modal)
            } else if ($t.hasClass('config')) {
                if (url) {
                    if (param)
                        param = app.toJSON(param);
                    app.block();
                    app.ajax(url, param, function (data) {
                        app.unblock();
                        if (data.code === 200) {
                            modal = $(app.template(text, data));
                            buildModal(modal);
                        } else {
                            app.showMsg(data.msg);
                        }
                    })
                } else {
                    buildModal($(text));
                }
            }
        });

        // 构建bootstrap模态框
        function buildModal($obj) {
            var opt = {
                keyboard: false,
                backdrop: 'static',
                show: true
            };

            $obj.modal(opt).on('hidden.bs.modal', function () {
                app.loadModule(null, true);
                // 隐藏的时候移除
                $obj.remove();
            })

        }

        // 弹出模态框的确认按钮点击事件，即提交模态框里面的form表单
        $body.on('click', '.modal-footer .btn-primary', function () {
            var $t = $(this);
            var modal = $t.parents('.modal-dialog');
            var mContent = $t.parents('.modal-content');
            var mBody = mContent.find('.modal-body');
            var $form = mContent.find('form');
            var eContent = mContent.find('.error-msg');
            if ($form.length > 1) {
                $form = mContent.find('.tab-pane.active form')
            }

            // 校验
            if (!$form.validationEngine('validate'))
                return;

            app.block(modal);
            // 异步提交表单
            app.ajaxSubmit($form, function (data) {
                app.unblock(modal);
                if (data.code == 200) {
                    $t.addClass('disabled');
                    mBody.html(app.buildSuccMsg('操作成功', 'fa-smile-o'));
                    app.loadModule(null, true);
                } else  {
                    eContent.html(app.buildMsg(data.msg))
                }
            })
        });

        /**
         * 表格的点击事件
         */
        $wrapper.on('click', '.box .table-hover tbody tr', function () {
            $(this).toggleClass('active');
        });



        /**
         * 模块下面的连接点击事件
         */
        $wrapper.on('click', '.box .btn', function () {
            var $t = $(this),
                templateId = $t.data('template-id'),
                url = $t.data('url'),
                $box,
                type = $t.data('type'),
                text,
                param = $t.data('param');

            if ($t.hasClass('btn-link')) {
                if (!templateId) return;
                text = $('#' + templateId).html();
                param = param ? app.toJSON(param) : {};
                if (type == 'video') {
                    buildModal($(app.template(text, param)));
                    setTimeout(function () {
                        VideoJS.autoSetup();
                    }, 200);
                    return;
                }
                if (url) {
                    app.block();
                    app.ajax(url, param, function ( data ) {
                        app.unblock();
                        if (data.code === 200) {
                            buildModal($(app.template(text, data)))
                        } else {
                            app.showMsg(data.msg);
                        }
                    })
                } else {
                    buildModal($(app.template(text, {})));
                }
            } else if ($t.hasClass('btn-primary')) {
                $box = $t.parents('.box');
                $box.find('form').find('[name="pageNum"]').val(1)
                app.loadModule($box);
            }
        });

        /**
         * 首页快捷键点击事件
         */
        $wrapper.on('click', '.first', function () {
            var $t = $(this);
            var url = $t.data('url');
            var mContent = $('#content-main');

            $('ul.sub li').removeClass('current');
            $t.parent().addClass('current');

            app.block();
            mContent.load(url, {time: new Date().getTime()}, function (responseText, textStatus) {
                var html;
                app.unblock();
                switch (textStatus) {
                    case 'timeout':
                        html = app.buildMsg('请求超时，请稍后再试');
                        break;
                    case 'error':
                        html = app.buildMsg('加载失败，请稍后再试');
                        break;
                    case 'success':
                        // 加载页面的模块
                        app.loadModule();
                        break;
                }
                if (html)
                    mContent.html('<div class="col-md-12">' + html + '</div>');
            });
            //弹模态框
            var $model = $(this),
            templateId = $model.data('template-id'),
                url2 = $model.data('url2'),
                $box,
                type = $model.data('type'),
                text,
                param = $model.data('param');
            if ($model.hasClass('addModel')) {
                if (!templateId) return;
                text = $('#' + templateId).html();
                param = param ? app.toJSON(param) : {};
                if (type == 'video') {
                    buildModal($(app.template(text, param)));
                    setTimeout(function () {
                        VideoJS.autoSetup();
                    }, 200);
                    return;
                }

                if (url2) {
                    app.block();
                    app.ajax(url2, param, function ( data ) {
                        app.unblock();
                        if (data.code === 200) {
                            buildModal($(app.template(text, data)))
                        } else {
                            app.showMsg(data.msg);
                        }
                    })
                } else {
                    buildModal($(app.template(text, {})));
                }
            } else if ($model.hasClass('btn-primary')) {
                $box = $model.parents('.box');
                $box.find('form').find('[name="pageNum"]').val(1)
                app.loadModule($box);
            }

            return false;
        });

        /**
         * 分页点击事件
         */
        $wrapper.on('click', '.pagination a', function () {
            var $t = $(this),
                $p = $t.parent(),
                $box = $t.parents('.box'),
                $form = $box.find('form');
            if ($p.hasClass('active') || $p.hasClass('disabled')) {
                return;
            }
            $p.addClass('active');

            $form.find('[name="pageNum"]').val($t.data('num'))

            app.loadModule($box);
        });

        // 初始化函数
        function init() {
            app.ajax('/haq-bms/user/detail', {}, function ( data ) {
                if (data.code === 200) {
                    // 导航
                    $('.navbar-nav').html(app.template($('#template-header').html(), data));
                    // 菜单
                    $('#main-menu').html(app.template($('#template-menu').html(), data));
                    //首页
                    var arrText = app.template($("#template-content-welcome").text(),data);
                    $("#content-main").html(arrText);
                } else {
                    if (data.msg) app.showMsg(data.msg);
                }
            })

        }
        // 初始化完毕

    })
})();