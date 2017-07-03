/**
 * app.js,定义系统公共函数
 */
(function () {
    /**
     * 修复 IE8 以下不支持 indexOf，导致产生错误
     */
    if (!Array.indexOf) {
        Array.prototype.indexOf = function (obj) {
            for (var i = 0; i < this.length; i++) {
                if (this[i] == obj) {
                    return i;
                }
            }
            return -1;
        };
    }
    // 新增日期格式化函数
    Date.prototype.format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };

    if (typeof define === 'undefined') {
        return;
    }
    // 定义全局公用方法
    define(['jquery', 'doT', 'store', 'messenger', 'blockUI','jQueryForm'], function ($, doT, store, messenger) {

        $(function () {
            // 按enter键 .fm-submit样式的按钮触发点击事件
            $(document).on('keydown', function (e) {
                if (e.keyCode === 13) {
                    $(':focus').parents('form:eq(0)').find('.fm-submit,.btn-primary').trigger('click');
                }
            })
        });

        $.wipeNull = function (str) {
            var arr = [null, 'null', undefined, 'undefined'];
            for (var i = 0; i < arr.length; i++) {
                if (arr[i] === str) return '';
            }
            return str;
        };

        return {
            /**
             * 添加jQuery扩展工具
             */
            wipeNull: function (str) {
                $.wipeNull(str);
            },
            /** parse json*/
            toJSON: function (data) {
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
            },
            /**
             * 转换form为json串
             */
            formToJson: function ($from) {
                var arr = $from.serializeArray(),
                    json = {};
                $.each(arr, function () {
                    if (json[this.name]){
                        if ($.isArray(json[this.name])){
                            json[this.name].push(this.value);
                        }else {
                            json[this.name]=[json[this.name],this.value];
                        }
                    }else {
                        json[this.name] = this.value;
                    }
                });
                return json;
            },

            /**
             * 基于doT.js模板封装
             */
            // 调用模板，tempId定义的模板id,data数据源
            template: function (template, data) {
                return doT.template(template)(data);
            },

            // 基于store.js 封装的本地存储功能
            store: function (key, value) {
                if (arguments.length === 1) {
                    return store.get(key);
                } else if (arguments.length >= 2) {
                    store.set(key, value);
                }
            },

            /**
             *  在元素上添加loading层
             */
            block: function (el) {
                var load = ['<div class="help"></div>', '<div class="eye"></div>', '<div class="load"><img src="/img/loaders/4.gif"></div>']
                    , opt = {
                    message: '<div class="eye"></div>',
                    css: {
                        border: 'none',
                        padding: '2px',
                        backgroundColor: 'none'
                    },
                    overlayCSS: {
                        backgroundColor: '#000',
                        opacity: 0.07,
                        cursor: 'wait'
                    }
                }
                this.testStyle('animation') ? opt.message = load[0] : opt.message = load[2];
                el ? el.block(opt) : $.blockUI(opt);
            },
            /**
             * 去除元素的loading
             * @param el
             */
            unblock: function (el) {
                if (el) {
                    el.unblock();
                } else {
                    $.unblockUI();
                }
            },
            /**
             * 构建消息
             */
            buildSuccMsg: function (msg, icon) {
                icon = icon ? icon : 'fa-smile-o';
                var close = '<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'
                return '<p class="alert alert-success" role="alert"><span class="fa ' + icon + '"></span>&nbsp;' + close + msg + '</p>';
            },
            buildMsg: function (msg, icon) {
                icon = icon ? icon : 'fa-frown-o';
                var close = '<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'
                return '<p class="alert alert-danger" role="alert"><span class="fa ' + icon + '"></span>&nbsp;' + close + msg + '</p>';
            },
            showMsg: function (message) {
                Messenger.options = {
                    extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
                    theme: 'flat'
                };
                if (message)
                    Messenger().error({
                        message: message,
                        showCloseButton: true
                    })
            },
            /**
             * 加载模模块,不传入参数则回去刷新所有的module
             *
             * @param module div.box的jQuery对象
             */
            loadModule: function (module, wasPage) {
                if (!module) {
                    module = $('#content-main .box');
                }
                var _this = this;
                module.each(function () {
                    var $t = $(this),
                        url = $t.data('url'),
                        $form = $t.find('form'),
                        data = _this.formToJson($form),
                        template = $t.data('template-id'),
                        body = $t.find('.box-body'),
                        html;
                    // 加载数据
                    if (url) {
                        _this.block($t);
                        if(wasPage === true) {
                            data.pageNum = module.find('.pagination li.active a').data('num') || 1;
                        }
                        _this.ajax(url, data, function (data) {
                            if (data.code === 200) {
                                html = _this.template($('#' + template).html(), data);
                                if ($t.data('type') === 'upload') {
                                    setTimeout(function () { _this.initUploadPic(data.token, data.domain); }, 200)
                                }
                            } else {
                                html = _this.buildMsg(data.msg);
                            }
                            _this.unblock($t);
                            body.html(html);
                            _this.page($t);
                        });
                    }
                })
            },
            /**
             * 对$box进行分页
             * @param $box
             */
            page: function ($box) {
                var $page = $box.find('.page'),
                    size = $page.data('size') || 10,
                    num = $page.data('num') || 1,
                    count = $page.data('count') || 0,
                    show = 6,
                    html = '',
                    start,
                    end,
                    total;
                size = parseInt(size);
                num = parseInt(num);
                count = parseInt(count);
                total = parseInt(count / size) + (count % size == 0 ? 0 : 1)

                if (total < 1) return
                // 计算需要显示的开始num和结束的num
                if (total > show) {
                    if (num > show / 2) {
                        if (num <= total - show + 1) {
                            start = num - parseInt(show / 2);
                            end = num + (show - parseInt(show / 2) - 1);
                        } else {
                            start = total - show + 1;
                            end = total;
                        }
                    } else {
                        start = 1;
                        end = show;
                    }
                } else {
                    start = 1;
                    end = total;
                }

                html += '<li class="' + (num == 1 ? 'disabled' : '') + '"><a data-num="' + 1 + '">首页</a></li>';
                html += '<li class="' + (num == 1 ? 'disabled' : '') + '"><a data-num="' + (num - 1) + '">上页</a></li>';

                for (var i = start; i <= end; i++) {
                    html += '<li class="' + (num == i ? 'active' : '') + '"><a data-num="' + i + '">' + i + '</a></li>';
                }
                html += '<li class="' + (num == total ? 'disabled' : '') + '"><a data-num="' + (num + 1) + '">下页</a></li>';
                html += '<li class="' + (num == total ? 'disabled' : '') + '"><a data-num="' + total + '">末页</a></li>';

                html = '<ul class="pagination">' + html + '</ul>'
                $page.find('.pull-left').html('<div>共' + count + '条数据，当前第' + num + '/' + total + '页</div>');
                $page.find('.pull-right').html(html);
            },
            testStyle: function (cssStyle) {
                var body = document.documentElement.style;
                for (var style in body) {
                    if (style === cssStyle) {
                        return true;
                    }
                }
            },
            /**
             * 封装的异步请求函数
             */
            ajax: function (url, data, complete, type) {
                var _this = this;
                $.ajax(url, {
                    type: type ? type : 'post',
                    timeout: 30000,
                    data: data,
                    cache: false,
                    headers: {'Accept-Type': 'application/json'},
                    complete: function (jqXHR, textStatus) {
                        if ($.isFunction(complete)) {
                            var json = {code : 444 , msg : '请求失败，请稍后再试'};
                            switch (textStatus) {
                                case 'success':
                                    if(jqXHR && jqXHR.responseText) {
                                        json  = _this.toJSON(jqXHR.responseText);
                                    }
                                    break;
                                case 'timeout':
                                    json = {code : 444 , msg : '请求超时，请稍后再试'}
                                    break;
                            }
                            complete.call(null, json);
                        }
                    }
                })
            },
            /**
             * 异步提交表单
             */
            ajaxSubmit : function ($form , complete ) {
                if(!$form || $form.length === 0) {
                    $.error('没有需要提交的表单元素');
                    return;
                }
                var _this = this;
                $form.ajaxSubmit({
                    headers: {'Accept-Type': 'application/json'},
                    complete: function (jqXHR, textStatus) {
                        if ($.isFunction(complete)) {
                            var json = {code : 444 , msg : '请求失败，请稍后再试'};
                            switch (textStatus) {
                                case 'success':
                                    if(jqXHR && jqXHR.responseText) {
                                        json  = _this.toJSON(jqXHR.responseText);
                                    }
                                    break;
                                case 'timeout':
                                    json = {code : 444 , msg : '请求超时，请稍后再试'}
                                    break;
                                case 'error':
                                    if (jqXHR.status === 400) {
                                        json = {code : 400 , msg : '您填写的参数不正确，请检查正确过后再重试'}
                                    } else if (jqXHR.status === 404) {
                                        json = {code : 404 , msg : '您请求的地址已不存在'}
                                    }
                                    break;
                            }
                            complete.call(null, json);
                        }
                    }
                })
            }

        }
    })
})();