/**
*@desc 明星红包活动详细配置
*@author: gengxiaofei@
* */
var udacheActivityStarConfig = {
    //项目基础路径
    //baseUrl : 'http://10.10.10.106:49210/webapp/pages/activity/star/', 
    baseUrl : 'http://static.udache.com/gulfstream/webapp/pages/activity/star/',
    //分享券系统链接
    //voucherSystem : 'http://10.10.10.106:49210/activity/hongbao/season/star/index?channel=6e66329891cbb9adb1966e44379sfad1',
    voucherSystem : 'http://gsactivity.diditaxi.com.cn/gulfstream/activity/v2/season/star/index?channel=6e6632b98d91cbb9adb19ce44379sfad1',
    //腾讯cos地址
    tencentCOS : 'http://cos.myqcloud.com/11000573/',
    //生成海报页面
    //老的
    //makePoster : 'http://gsactivity.diditaxi.com.cn/gulfstream/star/poster/index.html',
    //新的
    //makePoster : 'http://10.10.10.106:49210/webapp/pages/activity/star/poster.html',
    makePoster : 'http://static.udache.com/gulfstream/webapp/pages/activity/star/poster.html',
    makePosterWeixin : 'http://pay.xiaojukeji.com/gulfstream/star/index.htm',
    //默认首页
    index : 'http://static.udache.com/gulfstream/webapp/pages/activity/star/index.html',
    //index : 'http://10.10.10.106:49210/webapp/pages/activity/star/index.html',
    posterMsg : {
    default : '<p class="title">点【免费制作】做滴滴体海报红包</p><p class="title">分享给好友，让好友领红包</p>',
        'no-support' : '<p class="title">点【晒海报】分享滴滴体</p><p class="title">让好友领取红包</p>'

    },
    //抢红包成功后配置文案
    successMsg: {
        personal : '恭喜你抢到「{name}」的个性红包，马上制作你的个性红包吧!',
        star : '恭喜你抢到「{name}」的百万红包，马上制作你的个性红包吧!',
    },
    //默认抢红包文案
    indexMsg : {
        star : '抢「{name}」的百万红包！',
        personal: '抢「{name}」的个性红包！',
    },
    //默认已签过的文案
    fetched : '已经领过「{name}」的个性红包啦，马上制作属于自己的个性红包吧！',
    //默认头像地址
    headImg : 'http://static.udache.com/gulfstream/webapp/pages/activity/star/image/share_img.png',
    //分享按钮文案
    shareBtnText : '分享「{name}」的红包',
    //分享提示文案,//遮罩层|箭头
    shareTip : '点[发送给朋友]<br />分享「{name}」的红包',
    //券系统的title
    title : '抢红包通用版',
    //分享内容配置
    share : {
        appid: "wx69b6673576ec5a65",
        title: {
            default : '创意海报秀出自己，千万红包等你来拿！' ,
            personal : '马上抢「{name}」的个性红包吧！',
            star : '马上抢「{name}」的百万红包吧！',
        },
        desc: {
            default : '滴滴打车可以免费制作个性红包，马上邀请好友一起参与吧。',
            personal : '领专车券，制作个性海报，秀出你的创意。',
            star : '跟我一起来制作独一无二的个性红包吧。'
        },
        link: "",
        img_url: ""
    },
	star: {
		'yangyi': {
			name: '杨毅',
		},
        'meiting' : {
            name : '梅婷' 
        },
        'dapeng' : {
            name : '大鹏'
        },
        'huxia' : {
            name : '胡夏'
        },
        'liuyuxi' : {
            name : '刘语熙'
        },
        'tenghaibin' : {
            name : '滕海滨'
        },
        'wangxuebing' : {
            name : '王学兵' 
        },
        'xiongnaijin' : {
            name : '熊乃瑾' 
        },
        'zhanglanxin' : {
            name : '张蓝心' 
        },
        'zhangyao' : {
            name : '张瑶'
        },
        //第二批
        'duanxuan' : {
            name : '段暄' 
        },
        'haiyang' : {
            name : '海阳'
        },
        'lijinyu' : {
            name : '李金羽'
        },
        'liyi' : {
            name : '李毅'
        },
        'liuailing' : {
            name : '刘爱玲' 
        },
        'haiqing' : {
            name : '海清'
        },
        'sunjian' : {
            name : '孙坚' 
        },
        'huashuo' : {
            name :'华硕笔记本'
        },
        'huashuopad' : {
            name : '华硕平板' 
        },
        'tencentMap' : {
            name : '腾讯地图'
        }, 
        'zhongxinlvyou' : {
            name : '众信旅游'
        },
        'changyou' : {
            name : '天龙八部3D' 
        },
        'amd' : {
            name : 'AMD' 
        },
        'bashatv' : {
            name : '芭莎TV'
        },
        'zhangweijian' : {
            name : '张卫健'
        },
        'langlang' : {
            name : '郎朗'
        },
        'tangyan' : {
            name : '唐嫣'
        },
        'pingxuan' : {
            name : '评最牛滴滴体',
            title : '评最牛滴滴体，抢专车红包',
            img_url : 'http://static.udache.com/gulfstream/webapp/pages/activity/star/image/ddcar_new.png',
            successMsg : '恭喜你拿到了滴滴专车红包，可用于滴滴专车车费哦。',
            indexMsg : '滴滴体海报大赛送了你一个超大红包，快来领取吧',
            makePoster : false,
            shareButtonText : '邀好友领红包',
            activityDesc : '限专车开通城市使用',
            share : {
                title :'评最牛滴滴体，抢专车红包',
                desc :'一起参加滴滴体海报大赛评选，红包大礼等你来拿',
                link : 'http://hd.udache.com'
            }
        },
        'sumang' : {
            name : '苏芒',
        },
        'duchun' : {
            name : '杜淳' 
        },
        'yaobunengting' : {
            name : '滴滴专车',
            title : '药不能停，“专”治节后不想上班综合症',
            index_logo_url : 'http://static.udache.com/gulfstream/webapp/pages/activity/star/image/ddcar_new.png',            
            successMsg : '恭喜你抢到红包啦，分享给同事和朋友帮他们上班坐好一点~',
            indexMsg : '抢专车红包，上班路上对自己更好一点!',
            fetched : '你已经领过红包啦，分享给同事和朋友帮他们上班坐好一点~',
            makePoster : false,
            shareButtonText : '邀好友领红包',
            share :{
                 title:'抢滴滴专车红包，“专”治节后不想上班综合症',
                 desc:'滴滴专车接驾，上班路上对自己更好一点！',
                 img_url : 'http://static.udache.com/gulfstream/webapp/pages/activity/star/image/share_img.png',
                 link : 'http://gsactivity.diditaxi.com.cn/gulfstream/activity/v2/season/star/index?channel=7n111i1ee2d6cj2ccfdl9kdii171dn&star=yaobunengting'
            },
            activityDesc : '限专车开通城市<br />每个微信帐号限使用1张'
        }
	}
}

