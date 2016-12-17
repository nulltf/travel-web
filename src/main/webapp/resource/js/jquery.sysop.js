/**
 * 
 * 重新整理的SYSOP类+jQuery扩展
 * 
 * @author KOEN
 * 
 * 2011-7-20/26
 * 2011-8-12/8-22/9-7/9-15/9-29/10-8/10-24/10-31/11-1/11-9/11-15/11-18/12-5/12-7/12-13/12-20/12-22/12-27/1-10/1-12(修正addMsg bug)
 * 2-2/2-6(修改tipHover)/2-7(添加formItemInit)/2-8(更改validation的tipPar)/2-23(添加toggleTitleInit)/2-24(修改autoComplete的enterTarget)/3-29添加encodeJSONStr方法
 * 3-14(修改display)/3-22更新IP域检测/3-28 添加对在祥domBean的支持/3-29 validation增加对type=hidden input的支持,添加ruleClassOnSubmit类,getCookieVal()
 * 3-31/4-6汇总(issues+yyms+yydeploy)
 * 4-6(去掉整行验证)/4-17(改进checkbox全选)
 */
$.sysop={
	//
	//***** 弹出层类 *****
	//
	popup:{
		count:99,//初始化z-index
		ajaxStartCount:0,//Ajax次数记录
		getBox:function(o){
			var str=o&&o[o.href?'href':'alt'];
			var strBox=str&&str.slice(str.lastIndexOf('#'));
			return $(strBox);
		},
		box:function(box,css){
			this.btn=this.target;//保存最近BTN
			this.resetStart=false;//重置
			var $box=$(box||this.getBox(this.btn));
			if(css) $box.css(css);//自定义CSS@11-1
			if($box.is(':hidden')) this.count+=2;//只有box隐藏时z-index才自增，以兼容重复点击
			var docH=$(document).height(),boxH=$box.height(),winH=$(window).height();
			var varH=boxH+$(document).scrollTop(),bgH=boxH>winH?(varH>docH?varH+2:docH):docH;
			var top = (boxH>winH?0:($(window).height()-boxH)*0.4)+$(document).scrollTop();//0.4即4:6的比例
			var left = ($(document).width() - $box.width())/2;
			$('.pop-bg').css({height:bgH,zIndex:this.count,display:'block'});
			$box.css({top:top,left:left,zIndex:this.count+1,display:'block'});//增加1（2011.8.1）
		},
		bg:function(){
			this.btn=this.target;//保存最近BTN
			$('.pop-bg').css({height:$(document).height(),display:'block'});
		},
		drag:function(tit,e){//拖拽
			var $box=$(tit).parent().parent(),offset=$box.offset(),x=e.clientX-offset.left,y=e.clientY-offset.top,maxX=$(window).width()-$box.width()-2;	
			$(document).bind('mousemove.popup',function(e){
				var _left=e.clientX-x,left=_left<0?0:(_left>maxX?maxX:_left),top=e.clientY-y;
				$box.css({left:left,top:top});
			});
			$(tit).bind('mouseup.popup',function(){$(document).unbind("mousemove.popup").unbind("mouseup.popup");});
		},
		close:function(box){//关闭
			if(this.resetStart) return;//防止重复设置conut
			this.btn=this.target;//保存最近BTN
			var s=this.btn&&this.btn[this.btn.href?'href':'alt'],$box=$(box||s.substring(s.lastIndexOf('#')));
			$box.hide();//关闭BOX
			this.count-=2;
			if(this.count==99) $('.pop-bg').hide();
			else $('.pop-bg').css({zIndex:this.count});
		},
		tip:function(tit,cont,css,callback){//弹出提示框，参数css可省略设置
			if(typeof css!=='object') callback=css,css=undefined;
			$('#popTips').remove();
			$('body').append('<div id="popTips" class="pop-cont popTips"></div>');
			$('#popTips').html('<dl><dt>'+tit+'<a class="close" href="#popTips"></a></dt><dd><div class="cont"><div class="cont_t">'+cont+'</div></div><div class="bottom"><input type="button" alt="#popTips" class="ok close" value="确认" /></div></dd></dl>')
			.find('.close').click(callback||function(){});
			if(css) $('#popTips').css(css);
			this.box('#popTips');
		},
		autoTip:function(cont,fn){
			$('#popAutoTips').remove();
			$('body').append('<div id="popAutoTips" class="popAutoTips">'+cont+'</div>');
			$('#popAutoTips').show().delay(1000).hide(100,function(){fn&&fn();});
			//if(css) $('#popAutoTips').css(css);
		},
		confirm:function(tit,cont,css,callback,isCache,callback2){//弹出确认框，参数css可省略设置
			if(typeof css!=='object') callback2=isCache,isCache=callback,callback=css,css=undefined;
			if(!isCache) $('#popConfirm').remove();
			if(!$('#popConfirm').length){
				$('body').append('<div id="popConfirm" class="pop-cont popConfirm"></div>');
				$('#popConfirm').html('<dl><dt>'+tit+'<a class="close" href="#popConfirm"></a></dt><dd><div class="cont"><div class="cont_t">'+cont+'</div><div class="btn"><input type="button" alt="#popConfirm" class="ok close" value="确认" /> <input type="button" alt="#popConfirm" class="close" value="取消" /></div></div></dd></dl>')
				.find('.ok').click(function(){callback&&callback.call($('#popConfirm')[0])}).end().find('.close').click(function(){callback2&&callback2();});
			}
			if(css) $('#popConfirm').css(css);
			this.box('#popConfirm');
		},
		corner:function(box,fn){
			$(box).animate({bottom:'0px'},200);
		},
		reset:function(){
			this.count=99;
			this.resetStart=true;//防止冒泡触发.close
			$('.pop-cont').css({display:'none'});
			$('.pop-bg').css({zIndex:this.count,display:'none'});
		},
		init:function(){
			var self=this;//保存指针
			$(function(){
				//添加必要的html
				if(!$('.pop-bg').length) $('body').append('<div class="pop-bg"></div><div class="pop-bg-trans"></div><div class="pop-loading">Loading...</div>');
				//弹窗事件绑定
				$('.pop-btn').live('click',function(){//弹出本地窗口
					$.sysop.popup.box();return false;
				});
				$('.pop-btn-ajax').live('click',function(){//弹出AJAX窗口
					$.sysop.popup.bg();return false;
				});
				//loading提示和AJAX保存过程中的表单禁用
				var $input=$(':input:enabled'),isSave=false;
				//标记保存动作
				$(':button[value^="保存"],:button[value^="修改"]').click(function(){
					isSave=true;
				});
				//全局ajax开始
				$('.pop-loading').ajaxStart(function(){
					$(this).show();
					//延迟列队执行的保存
					setTimeout(function(){
						if(isSave) $('.pop-bg-trans').css({height:$('body').height()}).show();
					},0);
				});
				//全局ajax结束
				$('.pop-loading').ajaxStop(function(){
					$(this).hide();
					$('.pop-bg-trans').hide();//有无显示都hide以兼容.showTransBg();
					if(isSave) isSave=false;//删除$input.attr('disabled',false),
				});
			});
		},
		__init__:function(){//注意初始化顺序
			$(document).click(function(e){//记录
				if($(e.target).is('.pop-btn-local,.pop-btn,.close')) $.sysop.popup.target=e.target;
			});
			$('.pop-cont dt').live('mousedown',function(e){//拖拽
				$.sysop.popup.drag(this,e);
			});
			$('.close').live('click',function(e){//关闭
				$.sysop.popup.close();return false;
			});
		}()
	},
	tipClick:{//悬停弹出TIP
		addTip:function(){
			$('#tip-click-cont').remove();
			$('body').append('<div class="tip-click-cont" id="tip-click-cont"><span class="x">&times;</span></div>');
		},
		addEvent:function(){
			$('#tip-click-cont .x').click(function(){
				$(this).parent().hide();
			});
		},
		show:function(o,html){
			this.addTip();
			this.addEvent();
			var html=html?html:$(o).attr('tip'),$box=$('#tip-click-cont');
			$box.append(html);
			//
			var boxH=$box.outerHeight(),boxW=$box.outerWidth(),oH=$(o).outerHeight(),oW=$(o).outerWidth();//tip高度、目标高度
			var os=$(o).offset(),left=os.left,right=os.right,top=os.top;
			var scrTop=$(document).scrollTop(),cltTop=top-scrTop;
			if(boxH<cltTop) top=os.top-boxH;
			else top=os.top+oH;
			$box.css({left:left,top:top,display:'block'});
		},
		hide:function(){
			$('#tip-click-cont').hide();
		},
		init:function(fn){
			$('.tip-click').live('click',function(){$.sysop.tipClick.show(this);return false;});
			$(document).click(function(e){//记录
				if(!$(e.target).is('#tip-click-cont,.tip-click')) $('#tip-click-cont').hide();
			});
			//$('.tip-click').live('mouseout',function(){$.sysop.tipHover.hide(this);});
			fn&&fn();
		}
	},
	tipHover:{//悬停弹出TIP
		addTip:function(){
			$('#tip-hover-cont').remove();
			$('body').append('<div class="tip-hover-cont" id="tip-hover-cont"></div>');
		},
		addEvent:function(){
			var _this=this;
			$('#tip-hover-cont').hover(function(){
				clearTimeout(_this.timeout);
				$(this).show();
			},function(){
				var _o=this;
				_this.timeout=setTimeout(function(){
					_this.hide(_o);	
				},200);
			});
		},
		fixedPos:function(tar,msgBox){
			var $box=$(msgBox),$tar=$(tar);
			var boxH=$box.outerHeight(),boxW=$box.outerWidth();//tip高度
			var tarH=$tar.outerHeight(),tarW=$tar.outerWidth();//目标高度
			var os=$tar.offset(),left=os.left,top=os.top;//目标位移
			var scrTop=$(document).scrollTop(),cltTop=top-scrTop;
			var fixedTop,fixedLeft;
			//取得top
			if(boxH<cltTop) fixedTop=os.top-boxH;//正常
			else fixedTop=os.top+tarH;
			//取得left
			var right=$(document).width()-left;
			if(boxW<right) fixedLeft=left;//正常
			else fixedLeft=left-(boxW-tarW);
			//
			$box.css({left:fixedLeft,top:fixedTop});
			return $box;
		},
		addMsg:function(tar){
			var s=$(tar).attr('tip'),s1=s.slice(0,1),html;
			if(s1=='#') html=$(s).clone(true);
			else html=s;
			$('#tip-hover-cont').append(html);//只有一个弹出div实例，变的只是内容
		},
		show:function(tar){
			this.addTip();
			this.addEvent();
			this.addMsg(tar);
			//
			this.fixedPos(tar,'#tip-hover-cont').show();
		},
		hide:function(){
			$('#tip-hover-cont').hide();
		},
		init:function(fn){
			var _this=this;
			$('.tip-hover').live('mouseover',function(){
				var _o=this;
				clearTimeout(_this.timeout);
				_this.timeout=setTimeout(function(){
					_this.show(_o);
				},200);
			});
			$('.tip-hover').live('mouseout',function(){
				var _o=this;
				clearTimeout(_this.timeout);
				_this.timeout=setTimeout(function(){
					_this.hide(_o);	
				},200);
			});
			fn&&fn();
		}
	},
	tipAuto:{
		bind:function(tar,msg,fn){//fn用于自定义判断是否显示
			$(tar).find('.tipMsg').remove();
			if(localStorage[tar+'_msgShow']==='false') return;
			if(fn&&(fn()===false)) return;
			$(tar).css('position','relative').append('<div class="tipMsg"><p>'+msg+'</p><p class="aright"><a onclick="$.sysop.tipAuto.close(this,\''+tar+'\')">知道了</a></p></div>')
			.find('.tipMsg').fadeIn(200);
		},
		close:function(btn,tar){
			localStorage[tar+'_msgShow']=false;
			$(btn).closest('div.tipMsg').fadeOut('100');
		}
	},
	//
	//***** 分页类 *****
	//
	pagination:{
		toPage:function(n,self,fn){
		//alert(n);
			var num=typeof n=='string'?n:this.href.substring(this.href.lastIndexOf('#')+1);
			if(num/1>self.total/1||num/1<1||isNaN(num/1)) {alert('不存在此页面！');return;}
			fn(num);
		},
		initHTML:function(box,num,all,fn){
			var self=this;
			box.html('<ul><li> <a class="first" href="#">首页</a> <a class="prev" href="#">上一页</a> </li><li class="list"></li><li> <a class="next" href="#">下一页</a> <a class="last" href="#">尾页</a> </li><li class="goto">转到第 <input class="gotoPageValue" type="text" value="'+num+'" />/'+all+'页 <input type="button" class="gotoPageAction button" value="GO" /></li></ul>');
			var $goBtn=box.find('.gotoPageAction'),$pageInput=box.find('.gotoPageValue');
			$goBtn.click(function(){
				self.toPage($pageInput.val(),self,fn);
			});
			//绑定enter
			$.sysop.kit.bindEnterKey($pageInput);
		},
		init:function(pageBox,current,total,disNum,fn){//初始化分页
			var $pageBox=$(pageBox).show(),self=this;
			if(total==0){//当没有数据时处理
				if(!$pageBox.parent().find('tbody')[0].domBean) $pageBox.parent().find('tbody').html('<tr><td colspan="99">无记录...</td></tr>');
				$pageBox.css('display','none');
				return;
			}
			this.initHTML($pageBox,current,total,fn);//初始化html
			var cont=$pageBox.find('.list'),len=total,num=current-1,reObj=this,mid=Math.floor(disNum/2);
			this.total=total;
			cont.html('');//清空cont的html
			if(num>mid-1) var end=len-num>mid?num+mid+1:len,start=len>disNum?end-disNum:0;
			else var end=len>disNum?disNum:len,start=0;
			for(var i=start;i<end;i++) cont.append('<a class="num" href="#'+(i+1)+'">'+(i+1)+'</a>');//生成link
			var pageList=$pageBox.find('.num'),relIndex=num-start;
			pageList.eq(relIndex).replaceWith("<span class='cur'>" + pageList.eq(relIndex).text() + "</span>");//生成link和span结束
			$pageBox.find('.prev').attr('href','#'+num);
			$pageBox.find('.next').attr('href','#'+(num+2));
			$pageBox.find('.first').attr('href','#'+1);
			$pageBox.find('.last').attr('href','#'+total);
			pageAllLink=$pageBox.find('a');
			pageAllLink.unbind('click').show();
			pageAllLink.click(function(){self.toPage.call(this,undefined,self,fn);});//绑定点击
			if(num==0){$pageBox.find('.first,.prev').hide();}
			if(num==len-1){$pageBox.find('.last,.next').hide();}
		},
		displayNum:function(box,num,clickCallback,n){//每页显示记录
			//判断是否显示
			var $box=$(box);
			/*if(!box.parent().find('tbody tr').length){
				box.hide();
				return;
			}else{
				box.show();
			}*/
			var n1=n||10,n2=n1*2,n3=n1*5,n4=n1*10,t=+(+new Date);
			$box.html('<span class="tip-hover btn" tip="#displayNum_cont'+t+'">每页显示...</span><span id="displayNum_cont'+t+'" class="cont displayNumCont"><a rel="'+n1+'">'+n1+'条</a><a rel="'+n2+'">'+n2+'条</a><a rel="'+n3+'">'+n3+'条</a><a rel="'+n4+'" style="margin-right:0;">'+n4+'条</a></span>');
			if(num=='0') $('a',$box).eq(0).replaceWith("<span class='cur'>" + $('a',$box).eq(0).text() + "</span>");
			$('a',$box).each(function(){
				if($(this).attr('rel')==num) $(this).replaceWith("<span class='cur'>" + $(this).text() + "</span>");
			});
			$box.find('a').click(function(){
				clickCallback.call(this);
				$('#tip-hover-cont').hide();
			});
		}
	},
	//
	//***** 自动完成类 *****
	//
	autoComplete:{
		index:-1,
		end:true,
		init:function(input,url,key,enterTarget){//常规初始化----enterTarget会引起其两次提交？@8.23
			var $input=$(input),_parent=this,data;
			$input.wrap('<div class="autoComplete-wrap"></div>').after('<ul></ul>');
			var textKeyTip=$input.next(),sWidth=parseInt($input.css('width')),sHeight=parseInt($input.css('height'));
			textKeyTip.css({width:(sWidth?sWidth-2:'100%'),top:sHeight});//设置ul样式
			$input.blur(function(){//blur事件
				//if(_parent.end) $input.next().hide();
			});
			this.keyupEvent($input,$input.next(),url,key,data,enterTarget);
		},
		selectBox:function(select,param){//初始化selectBox
			if(param==undefined) param={};
			var _this=this,url=param.url,value=param.value,name=param.name,enterTarget=param.enterTarget,fn=param.fn;//fn为select的option数据加载完调用
			var select=$(select),selStr=select.find('option').eq(0).html(),selVal=select.val(),_parent=this;//加入储存默认值defVal
			var handlerData=function(data,name){//处理数据
				var nameStr=select.attr('rel')?'name="'+select.attr('rel')+'"':'';//加入rel属性判断@10.18
				select.wrap('<div class="selectBox-wrap"></div>').before('<input class="selectBoxInput" type="text" '+nameStr+' value="" placeholder="'+selStr+'" />').after('<ul></ul>');
				var textKey=select.prev(),textKeyTip=select.next(),sWidth=parseInt(select.css('width')),sHeight=parseInt(select.css('height'));
				textKey.css({width:sWidth-20,height:sHeight-2,lineHeight:sHeight-2+'px'});//设置input样式
				textKeyTip.css({width:sWidth-2});//设置ul样式
				if(url){
					select.html('<option value="'+selVal+'">'+selStr+'</option>');
					$.each(data,function(){
						select.append('<option value="'+this[value]+'">'+this[name]+'</option>');
					});
				}
				textKey.focus(function(){//记录原来初始值@8.23
					_this.oriInputValue=this.value;
				});
				textKey.blur(function(){//blur事件并赋值
					var self=this,hasVal=false;
					if(_this.oriInputValue==this.value) return;//如果没改变值则返回@8.23
					_this.oriInputValue=this.value;//记录赋值(防重复触发blur)@8.23
					if(self.value==''){//如果为空
						select.val(0);
						hasVal=true;
						return;
					}
					select.find('option').each(function(){//赋值select
						if($(this).html()==self.value) {select.val($(this).val()),hasVal=true,select.change();return false;}//让绑定事件触发;
					});
					if(!hasVal) select.append('<option value="'+this.value+'">'+this.value+'</option>'),select.val(this.value);
				});
				select.change(function(){
					if(select.val()!=0&&select.val()!=-1) textKey.val($(this).find('option:selected').text());
					else textKey.val('');
				});
				_parent.keyupEvent(textKey,textKeyTip,url,name,data,enterTarget);
				//
				_this.TEXTKEY=textKey,_this.SELECT=select;//储存
				//赋予默认值@11.18
				var defVal=select.attr('defValue'),defVal2=select.val(),defHTML=select.attr('defHTML');
				//赋值优先级defVal>defHTML>defVal2
				if(defVal){//
					select.val(defVal);
					select.change();
				}else if(defHTML){//加入默认HTML支持,即<option value="val">html</option>
					select.find('option').each(function(){
						if(this.innerHTML==defHTML){
							 $(this).attr('selected',true);
							 return false;
						}
					});
					select.change();
				}else if(defVal2&&defVal2!=='0'){//过滤'0'@12.27
					select.val(defVal2);
					select.change();
				}
				//兼容一开始select就为disabled的情况@11.1
				if(select.is(':disabled')) textKey.attr('disabled',true);
				//添加回调函数@12.8
				fn&&fn();
			};
			if(url){
				/*$.getJSON(url,function(data){
					if(data.success) data=data.object;//兼容新格式
					handlerData(data,name);
				});*/
				$.ajax({
					url:url,
					dataType:'json',
					success:function(data){
						if(data.success) data=data.object;//兼容新格式
						handlerData(data,name);
					},
					global:false
				});
			}else{
				var _data=[];
				select.find('option').each(function(){
					_data.push({value:$(this).text()});
				});
				handlerData(_data,'value');
			}
		},
		selectBoxChange:function(){//同步变化
			var _this=this;
			if(!_this.TEXTKEY) return;
			_this.TEXTKEY.val(_this.SELECT.find('option:selected').html());
		},
		keyupEvent:function(input,textKeyTip,url,key,data,enterTarget){//共用方法
			var _parent=this;
			var handlerJSON=function(json){//处理JSON函数
				textKeyTip.empty();
				$.each(json,function(){
					var item=key?this[key]:this;//兼容数组
					if(!data||(data&&item.toLowerCase().indexOf(input.val().toLowerCase())==0)){textKeyTip.append('<li>'+item+'</li>');}
				});
				if(textKeyTip.is(':parent')){
					textKeyTip.show();
					textKeyTip.scroll(function(){//UL滚动事件
						input.focus();
					}).find('li').mousedown(function(){//LI点击事件
						input.val($(this).html()).change();//新增change();
					}).mouseup(function(){
						input.focus();
					}).mouseover(function(){//LI悬入事件
						$(this).attr('class','hover').siblings().attr('class','');
						_parent.index=$(this).index();
					});
				} else {textKeyTip.hide();}
			};
			input.keyup(function(e){
				if(e.keyCode == '38'||e.keyCode == '40'||e.keyCode == '13') {
					if(textKeyTip.is(':visible')){
						if(textKeyTip.find('li.hover').length==0) _parent.index=-1;//初始化
						var target,len=textKeyTip.find('li').length;
						if (e.keyCode == '38'){//UP
							if(_parent.index<=0) _parent.index=len;//从尾开始
							target=textKeyTip.find('li').eq(_parent.index-1);
						}
						if(e.keyCode == '40') {//DOWN
							if(_parent.index==len-1) _parent.index=-1;//从头开始
							target=textKeyTip.find('li').eq(_parent.index+1);
						}
						if(target){//处理
							target.mouseover();
							input.val(target.html());
							//出现滚动条的处理
							var ulHeight=textKeyTip.height();
							var liHeight=textKeyTip.find('li').height();
							var liTop=liHeight*_parent.index;
							var ulScroll=textKeyTip.scrollTop();
							if(_parent.index==0) textKeyTip.scrollTop(0);//scroll到顶部
							else if(_parent.index==len-1) textKeyTip.scrollTop(liHeight*len);//scroll到底部
							else if((liTop-ulScroll)>(ulHeight-liHeight)) textKeyTip.scrollTop(ulScroll+liHeight);//scroll向下
							else if((liTop-ulScroll)<0) textKeyTip.scrollTop(ulScroll-liHeight);//scroll向上
						}
					}
					if(e.keyCode == '13') {//Enter回车
						textKeyTip.hide(),input.blur().change();//新增change();
						if(enterTarget){//搜索
							if(typeof enterTarget=='string') $(enterTarget).click();
							else enterTarget(input.val());
						} else if(enterTarget===undefined){
							input.parent().nextAll(':button:eq(0)').click();//搜索
						}else{
							input.focus();
						}
					}
				} else{
					var val = input.val();
					var relInput=$(input.attr('rel')),relStr='';//添加相关input判断@11.7
					if(relInput.length) relStr='&'+relInput.attr('name')+'='+relInput.val();
					if(val=='') {textKeyTip.hide();return;}//没值时退出
					if(!data){//正常处理
						$.ajax({
							global:false,
							url:url+input.val()+relStr,
							success:function(data){
								var json = data;
								//var json=eval('('+data+')');
								//var json=JSON.parse(data);
								if(json.success===false) return;
								if(json.success) json=json.object;//兼容新格式
								handlerJSON(json);//JSON
							}
						});
					}else{//selectBox处理
						handlerJSON(data);
					}
				};
			});
			$(document).click(function(e){//textKeyTip隐藏处理
				if(textKeyTip.is(':visible')){
					$(e.target).each(function(){
						if(this.name!==input[0].name) textKeyTip.hide();
					});
				}
			});
		}
	},
	//
	//***** 表单验证类 *****
	//
	validation:{
		addMsg:function(str,input){//添加信息
			this.obj=input?$(input):this.obj;//让input可自定义，这样可解决ajax的延迟导致tipPar判断错误问题
			this.tipPar=input?$(input).parent():this.tipPar;
			if(str===true){//正确
				this.delMsg(this.obj,true);
				//this.tipPar.append(this.hidCorTip?'':'<div class="inputTips correct"><ul class="tips"><li>✓ 正确</li></ul></div>');
			} else {//错误
				this.obj.addClass("errorStatus");
				this.tipPar.find('.correct').remove();
				//
				if(str==''||this.hidCorTip=='all') return;//添加可选隐藏全部提示信息（无论正确或错误，以兼容特殊格式的表单）
				var err=this.obj.next('.error');
				if(err.length){
					if(err.html().indexOf(str)==-1) err.find('.tips').append('<li>✗ '+str+'</li>');
					else return;
				}
				else this.obj.after('<div class="inputTips error"><ul class="tips"><li>✗ '+str+'</li></ul></div>');
			}
		},
		delMsg:function(input,isAll){//删除信息
			this.saveInput(input);//保存
			var $obj=this.obj,$par=this.tipPar;
			if(isAll) $par.find('.errorStatus').removeClass('errorStatus');//Par下的全部删除
			else $obj.removeClass('errorStatus');//只删除自己
			$obj.next('.inputTips').remove();//提示信息删除
		},
		myAddMsg:function(str,input){//自定义添加提示信息@11-1
			$(input).parent().find('.inputTips').remove();//不支持信息累加@11.9
			$(input).addClass("errorStatus");
			$(input).parent().append('<div class="inputTips error"><ul class="tips"><li>✗ '+str+'</li></ul></div>');
		},
		myDelMsg:function(input){//自定义添加提示信息@11-1
			$(input).removeClass("errorStatus");
			$(input).parent().find('.inputTips').remove();
		},
		test:{
			IP:function(str){return /^((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.){3}(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test($.trim(str));},
			date:function(str){//兼容2011-9-10和2011-9-10 11:39:43两种格式
				var sArr=$.trim(str).split(' ');
				if(sArr.length==1){
					return /^\d{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2]\d|3[0-1])$/.test($.trim(sArr[0]));
				}else{
					var b1=/^\d{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2]\d|3[0-1])$/.test($.trim(sArr[0]));
					var b2=/^(0?[0-9]|1\d|2[0-3]):(0?[0-9]|[1-5]\d):(0?[0-9]|[1-5]\d)$/.test($.trim(sArr[1]));
					return b1&&b2;
				}
			}
		},
		inputCheck:{
			ruleClassOnBlur:{//3.16重构规则,缺少'.requiredOneOfItem',日后请在业务页面上添加
				'.required':function(valid){//必填项
					if(!this.value) valid.addMsg('必填项');
				},
				'.ip':function(valid){//IP地址
					if(this.value&&!valid.test.IP(this.value)) valid.addMsg('IP地址不正确');
				},
				'.ipArea':function(self){//IP域，多个IP用回车分隔
					if(this.value){
						var arr=$.trim(this.value).split('\n'),err;
						for(var i=0;i<arr.length;i++){
							if(!self.test.IP(arr[i])){err=true;break;}
						}
						if(err) self.addMsg('IP域地址不正确');
					}
				},
				'.ipSection':function(self){//IP段
					var v1=$.trim($(this).val()),v2=$.trim($(this).siblings('.ipSection').val());
					if(v1&&!self.test.IP(v1)||v2&&!self.test.IP(v2)) self.addMsg('IP地址不正确');//同时验证v1/v2的IP地址
					if(v2){
						var s1=v1.substring(0,v1.lastIndexOf('.')),s2=v2.substring(0,v2.lastIndexOf('.'));
						if(s1!=s2) self.addMsg('IP段不匹配');
					}
				},
				'.number':function(valid){//数字
					if(this.value&&!/^\d+$/.test(this.value)) valid.addMsg('请输入数字');
				},
				'.word':function(valid){//英文名称
					if(this.value&&!/^[\w-]+$/.test(this.value)) valid.addMsg('请输入英文/数字/下划线/减号');
				},
				'.date':function(valid){//日期-兼容2011-9-10和2011-9-10 11:39:43两种格式
					if(this.value&&!valid.test.date(this.value)) valid.addMsg('日期不正确');
				}
			},
			ruleClassOnSubmit:{
				'.requiredSubmit':function(valid){//必填项
					if(!this.value) valid.addMsg('必填项');
				}
			},
			extend:function(ruleClass,type){//扩充规则函数，后面的规则会覆盖原来的ruleClass规则
				var self=$.sysop.validation.inputCheck,type=type||'blur';//默认扩展ruleClassOnBlur
				for(var p in ruleClass){
					if(type=='blur'){
						self.ruleClassOnBlur[p]=ruleClass[p];
					}else{
						self.ruleClassOnSubmit[p]=ruleClass[p];
					}
				}
			},
			trigger:function(valid,isSubmit){
				var this_=this;
				$.each(valid.inputCheck.ruleClassOnBlur,function(i,n){
					if($(this_).is(i)) n.call(this_,valid);
				});
				if(isSubmit){
					$.each(valid.inputCheck.ruleClassOnSubmit,function(i,n){
						if($(this_).is(i)) n.call(this_,valid);
					});
				}
			}
		},
		/*备份inputCheck，以防与曾经的检测不兼容
		inputCheck:function(self){//===========验证1（INPUT & TEXTAREA）
			if($(this).is('.ip')){//IP地址
				if(this.value&&!self.test.IP(this.value)) self.addMsg('IP地址不正确');
			}
			if($(this).is('.ipSection')){//IP段
				var v1=$.trim($(this).val()),v2=$.trim($(this).siblings('.ipSection').val());
				if(v1&&!self.test.IP(v1)||v2&&!self.test.IP(v2)) self.addMsg('IP地址不正确');//同时验证v1/v2的IP地址
				if(v2){
					var s1=v1.substring(0,v1.lastIndexOf('.')),s2=v2.substring(0,v2.lastIndexOf('.'));
					if(s1!=s2) self.addMsg('IP段不匹配');
				}
				//else return;
			}
			if($(this).is('.ipArea')){//IP域，多个IP用‘/’分隔
				if(this.value){
					var arr=$.trim(this.value).split('/'),err;
					for(var i=0;i<arr.length;i++){
						if(!self.test.IP(arr[i])){err=true;break;}
					}
					if(err) self.addMsg('IP域地址不正确');
				}
			}
			if($(this).is('.number')){//数字
				if(this.value&&!/^\d+$/.test(this.value)) self.addMsg('请输入数字');
			}
			if($(this).is('.svn')){//svn格式数据
				if(this.value&&!/^([1-9]+)\.([0-9]+)\.([0-9]+)$/.test(this.value)&&!/^([0-9]+)$/.test(this.value)) self.addMsg('请输入"数字.数字.数字或者纯数字"格式');
			}
			if($(this).is('.word')){//单词
				if(this.value&&!/^[\w-]+$/.test(this.value)) self.addMsg('请输入英文/数字/下划线/减号');
			}
			if($(this).is('.date')){//日期-兼容2011-9-10和2011-9-10 11:39:43两种格式
				if(this.value&&!self.test.date(this.value)) self.addMsg('日期不正确');
			}
			if($(this).is('.required')&&!$(this).closest('td').is(self.reqFilter)){//必填项
				//alert('d')
				if(!this.value) self.addMsg('必填项');
			}
			if($(this).is('.requiredOneOfItem')){//必填其中一项
				//alert($($(this).attr('rel')).length)
				var $item=$($(this).attr('rel')),err=true;
				$item.each(function(){
					if(this.value) {err=false;return false;}
				});
				if(err) self.addMsg('');
				else{
					$item.each(function(){
						$(this).removeClass('errorStatus');
						$(this).parent().find('.error').remove();
					});
				}
				//alert(err)
			}
			//验证input类结束
			if(this.value&&!self.tipPar.find('.error').length) self.addMsg(true);
		},*/
		selectCheck:function(self){//=============验证2（SELECT）
			if($(this).is('.required')&&!$(this).closest('td').is(self.reqFilter)){//必填项
				if(this.value=='0'||this.value=='-1') self.addMsg('必选项');
				else if(this==$('select:last',$(this).parent())[0]) self.addMsg(true);
			}
			if($(this).is('.related')){}//关联项
		},
		myCheck:{//==============验证3（自定义）
			duankou:function(input){//端口
				var self=this.parent,$input=$(self.form).find(input);
				$input.bind('check',function(){
					//self.tipPar=$(this).closest('.item');//自定义tips位置
					if(this.value!=0&&this.value!=-1){
						if(self.tipPar.find('.value').html()=='') self.addMsg('端口未选择');
					}
				});
			},
			unique:function(input,url1,url2){//检测名字唯一性
				var self=this.parent,$input=$(self.form).find(input),tipPar=$input.closest('td'),oriVal=$input.val(),url2=url2||'';
				$input.change(function(){
					if(this.value=='') return;
					$.getJSON(url1+this.value+url2,function(s){
						if(s===true||s.object===true) self.addMsg('已存在',$input);//ajax有延迟，所以需要提供原来的$input
						//else self.addMsg(true);
					});
				});
			},
			uniqueUDB:function(input,url1,url2){//检测是否存在此用户名
				var self=this.parent,$input=$(self.form).find(input),oriVal=$input.val(),url2=url2||'';
				$input.blur(function(){
					if(this.value==this.alt||this.Value==oriVal) return;
					$.get(url1+this.value+url2,function(s){
						if(s=='false') self.addMsg('UDB不存在此用户名');
						//else self.addMsg(true);
					});
				});
			},
			uniVal:function(input){//检测值唯一性(不需要与后台交互)
				var self=this.parent,$input=$(self.form).find(input);
				$input.blur(function(){
					if(this.value=='') return;
					var $input=$(self.form).find(input),len=$input.length,arr=[];
					for(var i=0;i<len;i++){
						if(this.value==$input.eq(i).val()) arr.push(this.value);
					}
					if(arr.length>1) self.addMsg('值不能重复');
				});
			},
			password:function(s,zh){
				var str="你的密码不符合以下格式：\n\n",n=0;
				if(!/^.{8,20}$/.test(s)) str+="·密码必须为8-20个字符\n",n++;
				//if(!/[a-z]+/.test(s)) str+="·至少有小写英文字母\n",n++;
				//if(!/[A-Z]+/.test(s)) str+="·至少有大写英文字母\n",n++;
				//if(!/[0-9]+/.test(s)) str+="·至少有数字\n",n++;
				//if(!/[\W_]+/.test(s)) str+="·至少有一个特殊字符\n",n++;
				if(zh&&new RegExp(zh).test(s)) str+="·不允许账号是密码的一部分\n",n++;
				if(/\s+/.test(s)) str+="·不允许空格\n",n++;
				//if(/[\u0391-\uFFE5]+/.test(s)) str+="·不允许使用中文或全角符号\n",n++;
				if(n!=0){
					alert(str+"\n请到运维中心修改密码格式，否则不允许登录！");
					return false;
				}
				else return true;
			},
			version:function(input){
				var self=this.parent,$input=$(self.form).find(input);
				$input.blur(function(){
					if(this.value=='') return;
					if(!/^[a-z0-9_.]+$/.test(this.value)) self.addMsg('只能输入小写字母 、数字、下划线、英文点');
				});
			}
		},
		saveInput:function(input){//保存input和它的parent
			this.obj=$(input);//保存1
			this.tipPar=$(input).parent();//保存2(默认tips位置)
		},
		valid:function(self,isSubmit){//触发验证方法，参数isSubmit保证只有check事件时才触发myCheck(可选择)
			self.saveInput(this);//保存
			if($(this).is('input,textarea')) self.inputCheck.trigger.call(this,self,isSubmit);
			if($(this).is('select')) self.selectCheck.call(this,self);
			if(isSubmit) $(this).trigger('onSubmit');//提交的自定义验证..说明：checkSubmit事件触发onSubmit和其它所有事件
		},
		check:function(form,isStatic){//手动验证表单（submit提交）
			var $form=form?$(form):$(this.form);
			//this.reqFilter='td:has(.valItem)';//必填项过滤器
			var $inputs=$form.find(':input');//:text,select,textarea改为:input以支持type=hidden的input
			$inputs.filter(':visible:not(:disabled)').trigger('checkSubmit');
			$inputs.filter('[type="hidden"]').trigger('checkSubmit');//添加type=hidden的支持(业务模块)@3-29
			//$form.find('.myCheck').filter(':visible:not(:disabled)').trigger('checkSubmit');//最后验证myCheck（可以省略？？）
			if($form.find('.errorStatus:visible').length||$form.find('.errorStatus[type="hidden"]').length) {//把.error改为.errorStatus:visible @9.26
				//$.sysop.popup.tip('错误','有 '+n+' 处错误，请重新输入！',function(){scroll(0,0);});return false;//取消弹出@12.22
				if(!isStatic) scroll(0,0);
				return false;
			}
			else return true;
		},
		init:function(form,hidCorTip,fn){//验证表单初始化
			var $form=$(form),self=this;
			this.form=form,this.myCheck.parent=this,this.hidCorTip=hidCorTip;//保存相关
			$form.addClass('validForm').find('.required,.requiredSubmit').closest('td').prev().prepend('<b class="starQR">*</b>');//格式化required样式
			//去掉整行验证@4-6
			/*$form.find('td').bind({//绑定td保证验证整行:input的同一时间触发
				'focusout':function(){$(this).find(':input:visible:enabled').trigger('check');}
			})*/
			$form.find(':input').bind({//验证事件
				'change':function(){self.delMsg(this);self.valid.call(this,self);},//前一版本为$(this).focus().blur()@9.28;现改回，因其有可能触发多次blur事件@10.12
				'blur':function(){self.valid.call(this,self);},
				'focus':function(){self.delMsg(this);},//把这功能放到change里面去@11.9//重新放开@12.22，因为搜索提示鼠标选择后没有去掉错误状态
				'check':function(){self.valid.call(this,self);},//当一行表单focusout时触发
				'checkSubmit':function(){self.valid.call(this,self,true);fn&&fn();}//当整个表单提交时触发
			});
			//表单保存按钮的可用性控制
			$.sysop.table.btnChange.init('.pop-cont','.pop-btn[value^="保存"],.pop-btn-ajax[value^="保存"]');
		},
		initSE:function(form){//自定义初始化（临时修补asu模版）
			var $form=$(form),self=this;
			this.form=form,this.myCheck.parent=this;//保存相关
			$form.addClass('validForm').find('.required').closest('div').prev().prepend('<b class="starQR">*</b>');//格式化required样式
			$form.find(':input').bind({//验证事件
				'blur':function(){
					if($(this).is('.required')&&this.value=='') self.myAddMsg('必填项',this);
				}
			});
		}
	},
	//
	//***** 菜单类 *****
	//
	menu:{
		tab:function(box,n,callback){//tab菜单
		if(n&&typeof n!=='number') callback=n,n=undefined;
		var $box=$(box),$titList=$box.children('.tit').children('li'),$contList=$box.children('.cont').children('li'),_this=this;
		$titList.click(function(){//绑定事件
			var index=$.inArray(this,$titList);
			$titList.removeClass('cur').eq(index).addClass('cur');
			if($contList.length>1) $contList.hide().eq(index).show();
			callback&&callback.call(this,index,$box);
		});
		$titList.bind('tab',function(){//绑定一个额外事件
			var index=$.inArray(this,$titList);
			$titList.removeClass('cur').eq(index).addClass('cur');
		});
		$titList.eq(n||0).click();//默认显示第1个tab
		}
	},
	//
	//可排序table
	//
	sortTable:{
		init:function(table,arrThStr){
			if(!$(table).length) return;
			this.initClass(table);
			this.bindEvent(table,arrThStr);
		},
		initClass:function(table){
			var $table=$(table).addClass('sort_table');
			//$table.find('thead').addClass('st_head');
			//$table.find('tbody').addClass('st_body');
			//$table.find('tr').addClass('st_row');
			//$table.find('td').addClass('st_td');
		},
		toggleArrow:function(td,$table){
			var $arrow=$(td).find('.arrow:visible');
			$table.find('thead .arrow').hide();
			if($arrow.length){
				if($arrow.is('.up')){
					$(td).find('.down').show();
				}else{
					$(td).find('.up').show();
				}
			}else{
				$(td).find('.up').show();
			}
		},
		bindEvent:function(table,arrThStr){
			var $table=$(table),$td=$table.find('thead td'),self=this;
			if(typeof arrThStr=='string'){//如果传递的是class
				var arr=$table.find(arrThStr);
			}else{
				var arr=[];
				for(var i=0;i<arrThStr.length;i++){//找出要排序的th字段td数组arr
					$td.each(function(){
						if($(this).text()==arrThStr[i]){
							arr.push($(this));
							return false;
						}
					});
				}
			}
			//点击排序
			$.each(arr,function(){
				var type=$(this).attr('sortType');//加入可自定义数据格式
				$(this).addClass('sortable');
				//添加箭头@12.14
				$(this).html('<span>'+$(this).text()+'</span><span class="arrow up">↑</span><span class="arrow down">↓</span>');
				$(this).click(function(){
					var $body=$table.find('tbody');
					var rows=$body.find('tr').get();
					var n=$(this).index();
					if (self.sortCol == n) {
						rows.reverse();
					} else {
						rows.sort(self.compare(n,type));
					}
					self.toggleArrow(this, $table);//箭头
					$body.append(rows);//用append保留了事件
					self.sortCol=n;
				});
			});
		},
		compare:function(iCol,type){
			var self=this;
			return  function compareTRs(oTR1, oTR2) {
					var v1 = $(oTR1).find('td:eq('+iCol+')').text();
					var v2 = $(oTR2).find('td:eq('+iCol+')').text();
					var dataType=type||(isNaN(v1)?'string':'float');//自动判断是否数字/字符串
					v1=self.convert(v1,dataType);
					v2=self.convert(v2,dataType);
					if (v1 < v2) {
						return -1;
					} else if (v1 > v2) {
						return 1;
					} else {
						return 0;
					}
			};
		},
		convert:function(html, dataType){//
			switch(dataType){
			case "int":
				return parseInt(html);
			case "float":
				return parseFloat(html);
			case "date":
				return new Date(Date.parse(html));
			case "bit"://容量单位
				if(html.indexOf('K')!=-1) return parseFloat(html);
				if(html.indexOf('M')!=-1) return parseFloat(html)*1000;
				if(html.indexOf('G')!=-1) return parseFloat(html)*1000*1000;
				if(html.indexOf('T')!=-1) return parseFloat(html)*1000*1000*1000;
			default:
				return html.toString();
			}
		}
	},
	//
	//***** 常用函数工具包 *****
	//
	kit:{
		browserCheck:function(){//浏览器检测 火狐 chrome都可以 20120711 update
			var ua = navigator.userAgent.toLowerCase();
		    if(ua.indexOf('chrome')!=-1 || ua.indexOf('firefox')!=-1 || ua.indexOf('msie 9.0') !=-1 || ua.indexOf('msie 10.0') !=-1){
		    	/*var ver=ua.match(/chrome\/([\d.]+)/)[1];
		    	var num=ver.slice(0,ver.indexOf('.'));
				if(num<10) {
					$('body').prepend('<div id="IEError" style="margin-top:-10px;font-size:16px;padding-top:100px;line-height:30px;width:100%;height:0;text-align:center;background:#FAFFBD;"><p><b>提示：</b>你的谷歌浏览器版本可能过低，为了正常使用本系统，请使用最新 谷歌浏览器(版本10或以上)。</p><p><a href="http://www.google.com/chrome?hl=zh-cn">点此下载最新版本</a></p></div>');
					$('#IEError').animate({height:200},500);
				}*/
		    } else {
		    	$('body').empty().prepend('<div id="IEError" style="margin-top:-10px;font-size:16px;line-height:30px;width:100%;text-align:center;background:#f1f1f1;"><p style="margin:100px 0 20px;"><p><b>提示：</b>本系统应用了最新WEB技术(HTML5及CSS3)，为了正常使用本系统，请使用 <b>谷歌浏览器</b>(版本10或以上)。</p><p><a href="https://www.google.com/intl/zh-CN/chrome/browser/?hl=zh-cn">点此下载最新版本</a></p></div>');
		    	return false;
			}
		},
		formItemInit:function(f,cls){
			var cls=cls||'.item',clsName=cls.slice(1);
			$(f).find(cls).each(function(){
				var p=$(this).parent();
				p.find('.add').click(function(){
					p.append(p.find(cls+'-copy').clone(true).attr('class',clsName));
					p.find('.del').attr('disabled',false);
				});
				p.find('.del').click(function(){
					$(this).closest(cls).remove();
					if(p.find(cls).length==1) p.find('.del').attr('disabled',true);
				});
				p.append($(this).clone(true).attr('class',clsName+'-copy none'));
				p.find('.del').attr('disabled',true);
			});
		},
		selectChange:function(box,url,value,name){//@2-24/一开始url参数为空获得第一个select的数据
			//初始化第一个select
			var $s1=$(box).find('select:eq(0)');
			$.getJSON(url,function(data){
				$.each(data.object,function(){
					$s1.append("<option value="+this[value||'value']+">"+this[name||'name']+"</option>");
				});
				$s1.change();
			});
			$(box).find('select').css('width','90');//固定长度
			//绑定事件
			$(box).find('select').change(function(){
				if(!$(this).next().is('select')) return;//当是最后select退出
				$(this).nextAll('select').html('<option value="">请选择</option>');
				if(!this.value) return;//当没值不传递后台
				var $s=$(this).next();
				$.getJSON(url+this.value,function(data){
					$.each(data.object,function(){
						$s.append("<option value="+this[value||'value']+">"+this[name||'name']+"</option>");
					});
				});
			});
		},
		getUrlValue:function(str,isEncodeUrl){//isEncodeUrl用于判断是否已转码@12.9
			var url=isEncodeUrl?location.href:decodeURI(location.href);
			var len=str.length;
			if(url.indexOf(str+'=')==-1) return '';
			else{
				var val=url.slice(url.indexOf(str+'=')+len+1);//取得等号后的字符串
				if(val.indexOf('&')==-1) return val;
				else return val.slice(0,val.indexOf('&'));
			}
		},
		obj2Str:function(o,isEncode){//包含JSON格式化，把双引号转为\"的形式
			var r = [],_this=this;
			if(o == null) return "null";
			if(typeof o == "string") return isEncode?'"'+encodeURIComponent(_this.toJSONStr(o))+'"':'"'+_this.toJSONStr(o)+'"';//可选URI转码@11.15
			if(typeof o == "object"){
			     if(!o.sort){
			       r[0]="{";
			       for(var i in o){
			         r[r.length]='"'+i+'"';
			         r[r.length]=":";
			         r[r.length]=_this.obj2Str(o[i],isEncode);
			         r[r.length]=",";
			       }
			       r[r.length-1]="}";
			     }else{
			       if(o.length==0) return "[]";
			       r[0]="[";
			       for(var i =0;i<o.length;i++){
			         r[r.length]=_this.obj2Str(o[i],isEncode);
			         r[r.length]=",";
			       }
			       r[r.length-1]="]";
			     }
			     return r.join("");
			  }
		   return o.toString();
		},
		toJSONStr:function(s){
			return s.replace(/"/g,'\\"');
			//return s.replace(/'/g,'\\\'');
		},
		encodeJSONStr:function(s){
			//var str=s.replace(/"/g,'\\"');
			//str=str.replace(/'/g,'\\\'');
			return escape(s);
		},
		str2Obj:function(s){
			return eval('('+s+')');
		},
		deParam:function(s,isObj){//把类似'name=koen&work=sys'转换成{"name":"koen","work":"sys"}形式
			if(!s) return "{}";
			var o={},len=s.match(/\=/g).length;
			var start=0;
			for(var i=0;i<len;i++){
				var v1=s.indexOf('=',start),v2=s.indexOf('&',start);
				sName=s.slice(start,v1);
				if(v2!=-1){
					sValue=s.slice(v1+1,v2);
					start=(v2+1);
				}
				else sValue=s.slice(v1+1);
				if(typeof o[sName]!=='undefined'){//增加支持checkbox数组
					var arr=[],val=o[sName];
					if(val.sort){
						o[sName].push(sValue);
					} else {
						arr.push(val,sValue);
						o[sName]=arr;
					};
				}else{//正常情况
					o[sName]=sValue;	
				}
			}
			return isObj?this.decodeJSON(o):this.obj2Str(this.decodeJSON(o));//增加解编码@12.28
		},
		serializeGT:function(box){//序列化BOX内含有name属性的元素值，包括INPUT元素
			var s='';
			$(box).find('[name]').each(function(){
				if($(this).is(':radio')){
					if(this.checked) {
						var val=encodeURIComponent(this.value);//编码@12.28
						s+=$(this).attr('name')+'='+val+'&';
					}
				}else if($(this).is(':checkbox')){
					if(this.checked){
						var val=1;
					}else{
						var val=0;
					}
					s+=$(this).attr('name')+'='+val+'&';
				}else{
					var val=$(this).is('span')?$(this).html():(this.value);
					val=encodeURIComponent(val);//编码@12.28
					s+=$(this).attr('name')+'='+val+'&';	
				}
			});
			return s.slice(0,-1);
		},
		exportJSON:function(box,isObj){//序列化BOX内含有name属性的元素值,并返回JSON格式数据(字符串)
			return this.deParam(this.serializeGT(box),isObj);
		},
		exportJSONByForm:function(f,isObj){//序列化form,并返回JSON格式数据(字符串)
			return this.deParam($(f).serialize(),isObj);
		},
		initFormByJSON:function(box,obj,keepInput,fn){//keepInput参数可省略
			if(typeof keepInput=='function') fn=keepInput;
			if(!obj) return;//防止obj为null
			if(keepInput!==true){
				$.each(obj,function(i,n){
					$(box).find('[name="'+i+'"]').each(function(){
						if($(this).css('display')=='none'||$(this).attr('type')=='hidden') $(this).replaceWith('<span name="'+i+'" class="none">'+n+'</span>');
						else $(this).replaceWith('<span name="'+i+'">'+n+'</span>');
					});
				});
				$(box).find('.tip').hide();//隐藏box的提示信息
				$(box).find(':input').hide();//隐藏box剩下的input
			}else {
				$(box).find(':text,select,textarea').val('');//先清空数据
				$.each(obj,function(i,n){
					var $i=$(box).find(':input[name="'+i+'"]');
					if($i.is(':checkbox')){//checkbox
						if(n.sort){//多个checkbox
							$.each(n,function(){
								$i.filter('[value="'+this+'"]').attr('checked',true);
							});
						}else{//单个
							if(this=='0') $i.attr('checked',false);//值0时为不选中
							else $i.attr('checked',true);//其它值都选中
						}
					}else if($i.is(':radio')){//radio
						$i.filter('[value="'+n+'"]').attr('checked',true);	
					}else{//正常input
						$i.val(n);
					}
				});
			}
			fn&&fn();
		},
		inserStr:function(index,s){
			
		},
		//不缓存JS、CSS文件批处理引入，注意script文件引入的结束字符串一定要"<\/script>"！
		noCacheFile:function(arr){
			var t=+new Date;
			for(var i=0;i<arr.length;i++){
				var s=arr[i].replace(/\.js/g,'.js?t='+t);
				s=s.replace(/\.css/g,'.css?t='+t);
				document.writeln(s);
			}
		},
		date:{
			checkTime:function(i){
				if (i<10) i="0" + i;
				return i;
			},
			getDateStr:function(dis){//负值是将来
				var D=new Date();
			    D.setDate(D.getDate()-(dis==undefined?0:dis));	
			    var date=D.getDate(),month=D.getMonth()+1,year=D.getFullYear();
			    return year+'-'+this.checkTime(month)+'-'+this.checkTime(date);
			},
			getTimeStr:function(dis1,dis2,all){//dis1为时间，dis2为日期，all是否显示全部
				var D=new Date();
				D.setDate(D.getDate()-(dis2==undefined?0:dis2));
				D.setHours(D.getHours()-(dis1==undefined?0:dis1));
				var date=D.getDate(),month=D.getMonth()+1,year=D.getFullYear();
				var h=D.getHours(),m=D.getMinutes(),s=D.getSeconds();
				//
				var dStr=year+'-'+this.checkTime(month)+'-'+this.checkTime(date);
				var tStr=this.checkTime(h)+':'+this.checkTime(m);//+':'+this.checkTime(s);屏蔽秒@12-6
				
				return all?(dStr+' '+tStr):tStr;
			},
			getAllDateStr:function(dis1,dis2){
				return this.getTimeStr(dis1,dis2,true);
			},
			setDate:function(input,dis){
				$(input).val(this.getDateStr(dis));
			},
			setDate2Date:function(startInput,endInput,dis){
				$(startInput).val(this.getDateStr(dis));
				$(endInput).val(this.getDateStr());
			},
			setAllDate2Date:function(startInput,endInput,dis1,dis2,hasSec){
				$(startInput).val(this.getAllDateStr(dis1,dis2)+(hasSec?':00':''));
				$(endInput).val(this.getAllDateStr()+(hasSec?':00':''));
			}
		},
		toggleTitle:function(t,noSign){
			var $tit=$(t),$cont=$($(t).attr('mid'));
			$tit.toggle(function(){
				if(!noSign){
					$(this).find('span').remove();
					$(this).prepend('<span>&and;</span>');
				}
				$cont.hide();
			},function(){
				if(!noSign){
					$(this).find('span').remove();
					$(this).prepend('<span>&or;</span>');
				}
				$cont.show();
			});
		},
		toggleTitleInit:function(cls){
			var cls=cls||'.toggle-tit';
			$(cls).each(function(){
				$(this).append('<span class="arr">&darr;</span>');
				var $c=$(this).attr('cont')?$($(this).attr('cont')):$(this).parent('.tit').next('.cont');
				$(this).click(function(){
					if($c.is(':visible')){
						$c.hide();
						$(this).find('.arr').html('&uarr;');
					}else{
						$c.show();
						$(this).find('.arr').html('&darr;');
					};
				});
			});
		},
		localSaveInputValue:function(input,name){
			if(localStorage[name]){
				$(input).val(localStorage[name]);
				//alert(localStorage[name])
			}
			$(input).change(function(){
				localStorage[name]=this.value;
			});
		},
		localSaveStatusByClick:function(btn,fn){//（动态生成html不支持保存）
			$(btn).each(function(i){
				var orgKey=localStorage['localSaveStatus'+i];
				if(orgKey){
					$(this).attr('status_save',orgKey);
					fn&&fn(this,orgKey);
				}
				$(this).click(function(){
					if($(this).attr('status_save')==='1'){
						var key='0';//key为0是原始状态，1为改变
					}else{
						var key='1';
					}
					$(this).attr('status_save',key);
					localStorage['localSaveStatus'+i]=key;
				});
			});
		},
		formLocalSave:{
			init:function(p){//p={form:'',submit:'',unique:true}
				this.$f=p.form?$(p.form):$('form:eq(0)');//默认第一个表单
				this.PageName=location.pathname;
				this.dataLocal=eval('('+(localStorage.formLocalSave||'{}')+')');
				//this.data=this.$f.serialize();
				var hasSave=$.sysop.kit.getUrlValue('localSave');
				if(p.unique){//唯一性
					if(hasSave){//刷新
						this.PageName=hasSave;
						this.initForm();
						//添加保存事件
						this.addEvent();
					}else{//新开
						this.addUrl();//重加载标ID
					}
				}else{//正常本地储存
					if(hasSave){//刷新
						this.initForm();
						//添加保存事件
						this.addEvent();
					}else{//新开
						var req=this.dataLocal[this.PageName];
						if(req){//存在历史数据
							var t=req.slice(req.lastIndexOf('=')+1);
							this.$f.before('<p id="formLocalSave_id">提示：您于 '+t+' 编辑的表单未保存，您希望<a onclick="$.sysop.kit.formLocalSave.load()">加载编辑</a>还是<a onclick="$.sysop.kit.formLocalSave.destroy()">忽略</a>？</p>');
						}else{
							this.addUrl();//重加载标ID
						}
					}	
				}
				//绑定submit按钮以摧毁本地保存
				var _this=this;
				p.submit&&$(p.submit).click(function(){
					_this.destroy();//摧毁数据
					window.onbeforeunload=null;//解除关闭保存事件
				});
			},
			saveForm:function(){
				this.dataLocal[this.PageName]=$.sysop.kit.serializeGT(this.$f)+'&formLocalSaveTime='+$.sysop.kit.date.getAllDateStr();
				localStorage.formLocalSave=$.sysop.kit.obj2Str(this.dataLocal);
			},
			initForm:function(){
				var req=this.dataLocal[this.PageName];
				if(!req) return;
				var json=$.sysop.kit.deParam(req,true);
				$.sysop.kit.initFormByJSON(this.$f,json,true);
			},
			destroy:function(){
				this.dataLocal[this.PageName]=null;
				localStorage.formLocalSave=$.sysop.kit.obj2Str(this.dataLocal);
				$('#formLocalSave_id').hide();
			},
			load:function(){
				this.addUrl();//重加载标ID
			},
			addEvent:function(){
				var _this=this;
				window.onbeforeunload=function(){_this.saveForm.call(_this);};//关闭或刷新保存
			},
			addUrl:function(){//重加载标ID
				var url=location.href;
				if(url.indexOf('?')==-1){
					location.href=url+'?localSave='+(+new Date);
				}else{
					location.href=url+'&localSave='+(+new Date);
				}
			}
		},
		bindEnterKey:function(input,btn){//绑定表单元素(input/select/textarea等)enter事件(绑定默认的enter提交表单禁掉)，默认绑定同层的btn，btn可以绑定相应按钮或回调函数
			var $input=input?$(input):$('.bind-enter');
			if(!$input.length) return;
			//
			$input.focus(function(){
				$(this).addClass('bind-enter-focus');
			}).blur(function(){
				$(this).removeClass('bind-enter-focus');
			});
			//禁用默认提交
			$input.closest('form').submit(function(){
				return false;
			});
			//
			$input.keydown(function(e){//keyup--keydown@9.29
				if(e.keyCode=='13'){
					if($('.bind-enter-focus').length){
						//var key=$('.bind-enter-focus').attr('enterkey'),$btn;//不再支持属性enterKey
						if(btn){
							if(typeof btn=='string') $btn=$(btn);
							else {btn();return;}//支持回调函数
						}else {
							$btn=$('.bind-enter-focus').parent().find(':button');
						}
						//
						$btn.click();
					}
				}
			});
		},
		phaseInputs:function(input1,input2,fn){
			var $p1=$(input1),$p2=$(input2);
			$p1.keyup(function(){
				$p2.val($p1.val());
				fn&&fn($p1,$p2);
			});
			$p1.blur(function(){
				$p2.val($p1.val());
				fn&&fn($p1,$p2);
			});
		},
		selectLoad:function(select,param){//{url,value,name,relInput,fn}
			var relStr=param.relInput?$(param.relInput).val():'',str=$(select).find('option').html(),s='<option value="0">'+(str||'请选择')+'</option>';
			$.getJSON(param.url+relStr,function(data){
				if(!data.success) return;
				$.each(data.object,function(){
					s+='<option value="'+this[param.value]+'">'+this[param.name]+'</option>'
				});
				$(select).html(s);
				param.fn&&param.fn();
			});
		},
		encodeJSON:function(o){
			for(var i in o){
				if(typeof o[i]==='string') o[i]=encodeURIComponent(o[i]);
				if(o[i].length&&o[i].sort){
					for(var j=0;j<o[i].length;j++) this.encodeJSON(o[i][j]);
				}
			}
			return o;
		},
		decodeJSON:function(o){
			for(var i in o){
				if(typeof o[i]==='string') o[i]=decodeURIComponent(o[i]);
				if(o[i].length&&o[i].sort){
					for(var j=0;j<o[i].length;j++) this.decodeJSON(o[i][j]);
				}
			}
			return o;
		},
		cloneObj:function(o){
			if(o == null||typeof(o) != 'object') return o;
			if(o&&o.sort){//数组
				var _o_=new Array();
				for(var i=0;i<o.length;i++) _o_[i] = this.cloneObj(o[i]);
			}else{//对象
				var _o_ = new Object();
				for(var i in o) _o_[i] = this.cloneObj(o[i]);
			}
			return _o_;
		},
		showTransBg:function(btn){//根据按钮显示透明遮罩层
			$(btn).live('click',function(){
				$('.pop-bg-trans').css({height:$('body').height()}).show();
			});
		},
		historyMark:function(input){//历史记录标记(用于点击返回原查询列表)
			var data={url:location.href,dom:{}};
			$('a[href]').live('click',function(){
				$(input).each(function(){
					data.dom[this.id]=this.value;
				});
				localStorage.historyMark=$.sysop.kit.obj2Str(data);
				//alert(localStorage.historyMark)
			});
			//如果存在历史
			//alert(localStorage.historyMark)
			if(localStorage.historyMark){
				var o=eval('('+localStorage.historyMark+')');
				if(o.url==location.href){
					$.each(o.dom,function(i,n){
						$('#'+i).attr('defValue',n).val(n);
					});
					localStorage.historyMark='';//销毁历史
				}
			}
		},
		checkboxSelect:function(box,relBtn,fn){//checkbox全选,relBtn可省
			var $box=$(box);
			var $ckAll=$box.find('thead td:eq(0) :checkbox');
			var $ckList=$box.find('tbody td:nth-child(1) :checkbox'),lenList=$ckList.length;
			//初始化按钮
			var hasRelBtn=relBtn&&typeof relBtn=='string';
			if(hasRelBtn) $(relBtn).attr('disabled',true);
			//复原
			$ckAll.attr('checked',false).unbind();
			//绑定事件
			$ckAll.change(function(){
				if(this.checked) $ckList.attr('checked',true);
				else $ckList.attr('checked',false);
				if(hasRelBtn){
					if(this.checked){
						$ckList.closest('tr').addClass('selected');
						$(relBtn).attr('disabled',false);
					}else{
						$ckList.closest('tr').removeClass('selected');
						$(relBtn).attr('disabled',true);
					}
					fn&&fn.call(this);
				}else{
					relBtn&&relBtn.call(this);
				}
				
			});
			$ckList.change(function(){
				var len=$box.find('tbody td:nth-child(1) :checked').length;
				if(len==lenList) $ckAll.attr('checked',true);
				else $ckAll.attr('checked',false);
				//
				if(hasRelBtn){
					if(this.checked){
						$(this).closest('tr').addClass('selected');
						$(relBtn).attr('disabled',false);
					}else{
						$(this).closest('tr').removeClass('selected');
						if(!$box.find('tr.selected').length){
							$(relBtn).attr('disabled',true);
						}
					}
					fn&&fn.call(this);
				}else{
					relBtn&&relBtn.call(this);
				}
			});
		},
		getCookieVal:function(name){
			var cookie=document.cookie;
			var offset=cookie.indexOf(name+'=');
			if(offset==-1) return '';
			var sIndex= cookie.indexOf ("=", offset)+1;
			var eIndex = cookie.indexOf (";", offset);
			if (eIndex == -1) eIndex = cookie.length;
			return unescape(cookie.slice(sIndex, eIndex));
		}
	},
	table:{
		checkbox:function(table,handle){
			var $table=$(table);
			$table.find('.checkAll-toggle').click(function(){
				$table.find(':checkbox').attr('checked',this.checked);
			});
			$table.find('tbody :checkbox').live('click',function(){
				var tb=$table.find('tbody'),len1=tb.find(':checkbox').length,len2=tb.find(':checked').length;
				if(len2==len1) $table.find('.checkAll-toggle').attr('checked',true);
				else $table.find('.checkAll-toggle').attr('checked',false);
			});
			handle&&handle($table);
		},
		prevNextInit:function(id,$tr,$nav,callback){
			checkDisabled($tr,$nav);
			$nav.find('.prev,.next').unbind().click(function(){
				if($(this).is('.disabled')) return false;
				var $dir=$(this).is('.prev')?$tr.prev('tr'):$tr.next('tr');
				var ID=$dir.attr(id);
				$tr=$dir;//保存	
				//
				callback&&callback(ID);
				checkDisabled($tr,$nav);
				return false;
			});
			function checkDisabled($tr,$nav){//检测前一个、后一个是否可用
				var $prev=$tr.prev('tr'),$next=$tr.next('tr');
				$nav.find('a').removeClass('disabled');
				if(!$prev.length) $nav.find('.prev').addClass('disabled');
				if(!$next.length) $nav.find('.next').addClass('disabled');
			}
		},
		btnChange:{
			BTN:null,
			init:function(form,btn){
				var $f=$(form),$btn=$f.find(btn);
				this.BTN=$btn;
				$btn.attr('disabled',true);
				$f.find(':text,textarea').keyup(function(){
					$btn.attr('disabled',false);
				});
				$f.find(':text,select').change(function(){
					$btn.attr('disabled',false);
				});
				$f.find(':button.btn-right').click(function(){
					$btn.attr('disabled',false);
				});
			},
			reset:function(){
				this.BTN.attr('disabled',true);
			}
		}
	}
};
//
//获得表单值的转义
//
;(function($){
	$.fn.extend({
		value:function(value){
			if(value!==undefined){
				return this.val(value);
			} else {
				return this.valTrans(this.val());
			}
		},
		valTrans:function(s){
			if(s!==''){
				var str=s.replace(/"/g,"\\\"");
				str=str.replace(/'/g,"\\'");
				return str;
			}
			return s;
		}
	})
})(jQuery);