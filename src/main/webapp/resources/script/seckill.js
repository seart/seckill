/**
 * Created by hutaishi@qq.com on 2017/1/3.
 */
// 存放主要交互逻辑js代码
// javascript模块化  通过json
    // seckill.detail.init(params)
var seckill = {
        URL: {
            now: function () {
                return '/seckill/time/now';
            },
            exposer: function (seckillId) {
                return '/seckill/' + seckillId + '/exposer';
            },
            execution: function (seckillId, md5) {
                return '/seckill/' + seckillId + '/' + md5 + '/execution';
            },

        },


        handleSeckillkill: function (seckillId, node) {
            // 获取秒杀地址，控制显示逻辑，执行秒杀
            node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
            $.post(seckill.URL.exposer(seckillId), {}, function (result) {
                if (result && result['success']) {
                    var exposer = result['data'];
                    if (exposer['exposed']) {
                        // 开发了秒杀
                        var md5 = exposer['md5'];
                        var killUrl = seckill.URL.execution(seckillId, md5);
                        console.log('killUrl: ' + killUrl); // TODO
                        // 绑定一次点击事件
                        $('#killBtn').one('click', function () {
                            // 执行秒杀请求
                            // 1.禁用按钮，避免二次请求
                            $(this).addClass('disabled');
                            // 执行秒杀
                            $.post(killUrl, {}, function (result) {
                                if (result && result['success']) {
                                    var killResult = result['data'];
                                    var state = killResult['state'];
                                    var stateInfo = killResult['stateInfo'];
                                    // 显示秒杀结果
                                    node.html('<span class="label label-success">' + stateInfo + '</span>');
                                }
                            });
                        });
                        node.show();
                    } else {
                        // 秒杀还没有开发
                        var now = exposer['now'];
                        var start = exposer['start'];
                        var end = exposer['end'];
                        // 计时
                        seckill.countdown(seckillId, now, start, end);
                    }
                } else {
                    // 请求异常
                    console.log('result: ' + result);
                }
            });
        },

        // 倒计时函数
        countdown: function (seckillId, nowTime, startTime, endTime) {
            var seckillbox = $('#seckill-box');
            if (nowTime > endTime) {
                seckillbox.html("秒杀结束");
            } else if (nowTime < startTime) {
                var killTime = new Date(startTime + 1000);
                seckillbox.countdown(killTime, function (event) {
                    // 时间格式
                    var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                    seckillbox.html(format);
                }).on('finish.countdown', function () {
                    seckill.handleSeckillkill(seckillId, seckillbox);
                });
            } else {
                // 秒杀开始
                seckill.handleSeckillkill(seckillId, seckillbox);
            }
        },


        validatePhone: function (phone) {
            if (phone && phone.length == 11 && !isNaN(phone)) {
                return true;
            } else {
                return false;
            }
        },

        detail: {
            // 详情页初始化
            init: function (params) {
                // 手机验证和登录，计时交互
                // 在cookie中查找手机号
                var killPhone = $.cookie('killPhone');


                if (!seckill.validatePhone(killPhone)) {
                    // 绑定phone
                    var killPhoneModal = $('#killPhoneMod');
                    killPhoneModal.modal({
                        // 显示弹出层
                        show: true,
                        backdrop: 'static', // 禁止位置关闭
                        keyboard: false  // 关闭键盘事件
                    });
                    $('#killPhoneBtn').click(function () {
                        var inputPhone = $('#killPhoneKey').val();
                        if (seckill.validatePhone(inputPhone)) {
                            // 电话写入cookie
                            $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                            // 刷新页面
                            window.location.reload();
                        } else {
                            $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                        }
                    });

                }
                var startTime = params['startTime'];
                var endTime = params['endTime'];
                var seckillId = params['seckillId'];
                $.get(seckill.URL.now(), {}, function (result) {
                    if (result && result['success']) {
                        var nowTime = result['data'];
                        seckill.countdown(seckillId, nowTime, startTime, endTime);
                    } else {
                        console.log('result: ' + result);
                    }
                });
            }
        }

    };