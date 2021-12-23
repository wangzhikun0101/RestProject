package com.westvalley.g.materiel.create;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import com.westvalley.g.sap.SAPCall;
import weaver.conn.RecordSet;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.action.BaseAction;
import weaver.workflow.request.RequestManager;

/**
 * 功能：校验必填项
 */

public class CheckField extends BaseAction {

	@Override
	public String execute(RequestInfo requestInfo) {
		String flag="1";
		// 获取流程主表信息
		Property[] properties = requestInfo.getMainTableInfo().getProperty();
		
		String sapxxfh="";//1.SAP返回消息
		String lyy="";//2.来源
		String yt="";//3.用途
		String aqkc="";//4.安全库存
		String sqr="";//5.申请人
		String rybh="";//6.人员编号
		String sqrq="";//7.申请日期
		String szgs="";//8.所属公司
		String szbm="";//9.所属部门
		String gw="";//10.岗位
		String bz="";//11.备注
		String fjsc="";//12.附件上传
		String xglc="";//13.相关流程
		String xgwd="";//14.相关文档
		String manager="";//15.manager
		String sqrsj="";//16.申请人上级
		String lsh="";//17.流水号
		String flags="";//18.flag
		String xyly="";//19.行业领域
		String gc="";//20.工厂
		String jwlh="";//21.旧物料号
		String sapwlbm="";//22.SAP物料编码
		String wlz="";//23.物料组
		String ms="";//24.物料描述
		String wlwb="";//25.物料文本
		String jbdw="";//26.基本单位
		String cpz="";//27.产品组
		String khdm="";//28.客户代码
		String khwlbm="";//29.客户物料编码
		String zzsbm="";//30.制造商编码
		String zzs="";//31.制造商
		String gys="";//32.供应商
		String tzbh="";//33.图纸编号
		String bbh="";//34.版本号
		String mz="";//35.毛重
		String jz="";//36.净重
		String zldw="";//37.重量单位
		String ywl="";//38.业务量
		String tjdw="";//39.体积单位
		String dxlg="";//40.大小/量纲
		String bzwxbzsl="";//41.标准外箱包装数量
		String zhwjbjldwdfm="";//42.转换为基本计量单位的分母
		String jbjldwzhfz="";//43.基本计量单位转换分子
		String zx1="";//44.属性1
		String zx2="";//45.属性2
		String zx3="";//46.属性3
		String bgzx1="";//47.属性4(电压)
		String bgzx2="";//48.属性5(品牌)
		String bgzx3="";//49.属性6
		String bgzx4="";//50.属性7
		String yph="";//51.样品号
		String sfcz="";//52.是否车载
		String nxbzsl="";//53.内箱包装数量
		String cbhbzsl="";//54.层/包/盒包装数量
		String pclx="";//55.批次类型
		String pcfl="";//56.批次分类
		String xszz="";//57.销售组织
		String fxqd="";//58.分销渠道
		String jhgc="";//59.交货工厂
		String sfl="";//60.税分类
		String cgz="";//61.采购组
		String cgdw="";//61.采购单位
		String pcgl="";//62.批次管理
		String yqd="";//63.源清单
		String cgjzdm="";//64.采购价值代码
		String cgdwb="";//65.采购单文本
		String mrplx="";//66.MRP 类型
		String mrpkzz="";//67.MRP 控制者
		String pldx="";//68.批量大小
		String zxpldx="";//69.最小批量大小
		String zdpldx="";//70.最大批量大小
		String srz="";//71.舍入值
		String cglx="";//72.采购类型
		String tscglx="";//73.特殊采购类型
		String scccdd="";//74.生产仓储地点
		String wbcgccdd="";//75.外部采购仓储地点
		String fc="";//76.反冲
		String zzsc="";//77.自制生产
		String cgjhjhsj="";//78.采购计划交货时间
		String shclsj="";//79.收货处理时间
		String jhbjm="";//80.计划边际码
		String clz="";//81.策略组
		String xhms="";//82.消耗模式
		String xhxhqj="";//83.向后消耗期间
		String xqxhqj="";//84.向前消耗期间
		String zhmrp="";//85.综合MRP
		String kyxjc="";//86.可用性检查
		String gbjz="";//87.个别/集中
		String scgly="";//88.生产管理员
		String scjhcswj="";//89.生产计划参数文件
		String pcgll="";//90.批次管理
		String wl="";//91.无卤
		String rohs20="";//92.ROHS2.0
		String tbjcbs="";//93.特别检查标识
		String hjdj="";//94.环境等级
		String bhlbejsz6p="";//95.不含邻苯二甲酸酯(6P)
		String bgbm="";//96.报关编码
		String bgmc="";//97.报关名称
		String hgjldw="";//98.海关计量单位
		String zhwjbjldwdfms="";//99.转换为基本计量单位的分母
		String jbjldwzhfzs="";//100.基本计量单位转换分子
		String ly="";//101.来源
		String sfwx="";//102.是否外销
		String tkbs="";//103.特控标识
		String smfl="";//104.税目分类
		String pcglls="";//105.批次管理
		String cfdd="";//106.存放地点
		String cgybh="";//107.仓管员编号
		String cctj="";//108.存储条件
		String zdsyhjsmtqsxrq="";//109.最短剩余货架寿命（提前失效日期）
		String pgl="";//110.评估类
		String jgkz="";//111.价格控制
		String jgdw="";//112.价格单位
		String jgqd="";//113.价格确定
		String hqscbgs="";//114.含QS成本估算
		String wlly="";//115.物料来源
		String lrzx="";//116.利润中心
		String cbhspl="";//117.成本核算批量
		String cym="";//118.差异码
		String khggsgysggs="";//119.客户规格书/供应商规格书
		String clmc="";//120.材料名称
		String sfmfkgcl="";//121.是否免费客供材料
		String sftxwz01="";//122.是否填写完整01
		String sftxwz02="";//123.是否填写完整02
		String sftxwz03="";//124.是否填写完整03
		String sftxwz04="";//125.是否填写完整04
		String sftxwz05="";//126.是否填写完整05
		String sftxwz06="";//127.是否填写完整06
		String sftxwz07="";//128.是否填写完整07
		String sftxwz08="";//129.是否填写完整08
		String wllx="";//130.物料类型
		
		//返回信息
		String wul="";
		String gongc="";
		String leix="";
		String xinx="";
		
		StringBuilder msg=new StringBuilder();
		
		for (Property p : properties) {
			if ("sapxxfh".equals(p.getName())) {//1.SAP返回消息
				sapxxfh = p.getValue();
				
			}else if ("lyy".equals(p.getName())) {//2.来源
				lyy = p.getValue();
			}else if("yt".equals(p.getName())){//3.用途
				yt=p.getValue();
			}else if("aqkc".equals(p.getName())){//4.安全库存
				aqkc=p.getValue();
			}else if("sqr".equals(p.getName())){//5.申请人
				sqr=p.getValue();
			}else if("rybh".equals(p.getName())){//6.人员编号
				rybh=p.getValue();
			}else if("sqrq".equals(p.getName())){//7.申请日期
				sqrq=p.getValue();
			}else if("szgs".equals(p.getName())){//8.所属公司
				szgs=p.getValue();
			}else if("szbm".equals(p.getName())){//9.所属部门
				szbm=p.getValue();
			}else if("gw".equals(p.getName())){//10.岗位
				gw=p.getValue();
			}else if("bz".equals(p.getName())){//11.备注
				bz=p.getValue();
			}else if("fjsc".equals(p.getName())){//12.附件上传
				fjsc=p.getValue();
			}else if("xglc".equals(p.getName())){//13.相关流程
				xglc=p.getValue();
			}else if("xgwd".equals(p.getName())){//14.相关文档
				xgwd=p.getValue();
			}else if("manager".equals(p.getName())){//15.manager
				manager=p.getValue();
			}else if ("sqrsj".equals(p.getName())) {//16.申请人上级
				sqrsj = p.getValue();
			}else if("lsh".equals(p.getName())){//17.流水号
				lsh=p.getValue();
			}else if("flag".equals(p.getName())){//18.flag
				flags=p.getValue();
			}else if("xyly".equals(p.getName())){//19.行业领域
				xyly=p.getValue();
			}else if("gc".equals(p.getName())){//20.工厂
				gc=p.getValue();
			}else if("jwlh".equals(p.getName())){//21.旧物料号
				jwlh=p.getValue();
			}else if("sapwlbm".equals(p.getName())){//22.SAP物料编码
				sapwlbm=p.getValue();
			}else if("wlz".equals(p.getName())){//23.物料组
				wlz=p.getValue();
			}else if("ms".equals(p.getName())){//24.物料描述
				ms=p.getValue();
			}else if("wlwb".equals(p.getName())){//25.物料文本
				wlwb=p.getValue();
			}else if("jbdw".equals(p.getName())){//26.基本单位
				jbdw=p.getValue();
			}else if("cpz".equals(p.getName())){//27.产品组
				cpz=p.getValue();
			}else if("khdm".equals(p.getName())){//28.客户代码
				khdm=p.getValue();
			}else if("khwlbm".equals(p.getName())){//29.客户物料编码
				khwlbm=p.getValue();
			}else if ("zzsbm".equals(p.getName())) {//30.制造商编码
				zzsbm = p.getValue();
			}else if("zzs".equals(p.getName())){//31.制造商
				zzs=p.getValue();
			}else if("gys".equals(p.getName())){//32.供应商
				gys=p.getValue();
			}else if("tzbh".equals(p.getName())){//33.图纸编号
				tzbh=p.getValue();
			}else if("bbh".equals(p.getName())){//34.版本号
				bbh=p.getValue();
			}else if("mz".equals(p.getName())){//35.毛重
				mz=p.getValue();
			}else if("jz".equals(p.getName())){//36.净重
				jz=p.getValue();
			}else if("zldw".equals(p.getName())){//37.重量单位
				zldw=p.getValue();
			}else if("ywl".equals(p.getName())){//38.业务量
				ywl=p.getValue();
			}else if("tjdw".equals(p.getName())){//39.体积单位
				tjdw=p.getValue();
			}else if("dxlg".equals(p.getName())){//40.大小/量纲
				dxlg=p.getValue();
			}else if("bzwxbzsl".equals(p.getName())){//41.标准外箱包装数量
				bzwxbzsl=p.getValue();
			}else if("zhwjbjldwdfm".equals(p.getName())){//42.转换为基本计量单位的分母
				zhwjbjldwdfm=p.getValue();
			}else if("jbjldwzhfz".equals(p.getName())){//43.基本计量单位转换分子
				jbjldwzhfz=p.getValue();
			}else if ("zx1".equals(p.getName())) {//44.属性1
				zx1 = p.getValue();
			}else if("zx2".equals(p.getName())){//45.属性2
				zx2=p.getValue();
			}else if("zx3".equals(p.getName())){//46.属性3
				zx3=p.getValue();
			}else if("bgzx1".equals(p.getName())){//47.属性4(电压)
				bgzx1=p.getValue();
			}else if("bgzx2".equals(p.getName())){//48.属性5(品牌)
				bgzx2=p.getValue();
			}else if("bgzx3".equals(p.getName())){//49.属性6
				bgzx3=p.getValue();
			}else if("bgzx4".equals(p.getName())){//50.属性7
				bgzx4=p.getValue();
			}else if("yph".equals(p.getName())){//51.样品号
				yph=p.getValue();
			}else if("sfcz".equals(p.getName())){//52.是否车载
				sfcz=p.getValue();
			}else if("nxbzsl".equals(p.getName())){//53.内箱包装数量
				nxbzsl=p.getValue();
			}else if("cbhbzsl".equals(p.getName())){//54.层/包/盒包装数量
				cbhbzsl=p.getValue();
			}else if("pclx".equals(p.getName())){//55.批次类型
				pclx=p.getValue();
			}else if("pcfl".equals(p.getName())){//56.批次分类
				pcfl=p.getValue();
			}else if("xszz".equals(p.getName())){//57.销售组织
				xszz=p.getValue();
			}else if ("fxqd".equals(p.getName())) {//58.分销渠道
				fxqd = p.getValue();
			}else if("jhgc".equals(p.getName())){//59.交货工厂
				jhgc=p.getValue();
			}else if("sfl".equals(p.getName())){//60.税分类
				sfl=p.getValue();
			}else if("cgz".equals(p.getName())){//61.采购组
				cgz=p.getValue();
			}else if("pcgl".equals(p.getName())){//62.批次管理
				pcgl=p.getValue();
			}else if("yqd".equals(p.getName())){//63.源清单
				yqd=p.getValue();
			}else if("cgjzdm".equals(p.getName())){//64.采购价值代码
				cgjzdm=p.getValue();
			}else if("cgdwb".equals(p.getName())){//65.采购单文本
				cgdwb=p.getValue();
			}else if("mrplx".equals(p.getName())){//66.MRP 类型
				mrplx=p.getValue();
			}else if("mrpkzz".equals(p.getName())){//67.MRP 控制者
				mrpkzz=p.getValue();
			}else if("pldx".equals(p.getName())){//68.批量大小
				pldx=p.getValue();
			}else if("zxpldx".equals(p.getName())){//69.最小批量大小
				zxpldx=p.getValue();
			}else if("zdpldx".equals(p.getName())){//70.最大批量大小
				zdpldx=p.getValue();
			}else if("srz".equals(p.getName())){//71.舍入值
				srz=p.getValue();
			}else if ("cglx".equals(p.getName())) {//72.采购类型
				cglx = p.getValue();
			}else if("tscglx".equals(p.getName())){//73.特殊采购类型
				tscglx=p.getValue();
			}else if("scccdd".equals(p.getName())){//74.生产仓储地点
				scccdd=p.getValue();
			}else if("wbcgccdd".equals(p.getName())){//75.外部采购仓储地点
				wbcgccdd=p.getValue();
			}else if("fc".equals(p.getName())){//76.反冲
				fc=p.getValue();
			}else if("zzsc".equals(p.getName())){//77.自制生产
				zzsc=p.getValue();
			}else if("cgjhjhsj".equals(p.getName())){//78.采购计划交货时间
				cgjhjhsj=p.getValue();
			}else if("shclsj".equals(p.getName())){//79.收货处理时间
				shclsj=p.getValue();
			}else if("jhbjm".equals(p.getName())){//80.计划边际码
				jhbjm=p.getValue();
			}else if("clz".equals(p.getName())){//81.策略组
				clz=p.getValue();
			}else if("xhms".equals(p.getName())){//82.消耗模式
				xhms=p.getValue();
			}else if("xhxhqj".equals(p.getName())){//83.向后消耗期间
				xhxhqj=p.getValue();
			}else if("xqxhqj".equals(p.getName())){//84.向前消耗期间
				xqxhqj=p.getValue();
			}else if("zhmrp".equals(p.getName())){//85.综合MRP
				zhmrp=p.getValue();
			}else if ("kyxjc".equals(p.getName())) {//86.可用性检查
				kyxjc = p.getValue();
			}else if("gbjz".equals(p.getName())){//87.个别/集中
				gbjz=p.getValue();
			}else if("scgly".equals(p.getName())){//88.生产管理员
				scgly=p.getValue();
			}else if("scjhcswj".equals(p.getName())){//89.生产计划参数文件
				scjhcswj=p.getValue();
			}else if("pcgll".equals(p.getName())){//90.批次管理
				pcgll=p.getValue();
			}else if("wl".equals(p.getName())){//91.无卤
				wl=p.getValue();
			}else if("rohs20".equals(p.getName())){//92.ROHS2.0
				rohs20=p.getValue();
			}else if("tbjcbs".equals(p.getName())){//93.特别检查标识
				tbjcbs=p.getValue();
			}else if("hjdj".equals(p.getName())){//94.环境等级
				hjdj=p.getValue();
			}else if("bhlbejsz6p".equals(p.getName())){//95.不含邻苯二甲酸酯(6P)
				bhlbejsz6p=p.getValue();
			}else if("bgbm".equals(p.getName())){//96.报关编码
				bgbm=p.getValue();
			}else if("bgmc".equals(p.getName())){//97.报关名称
				bgmc=p.getValue();
			}else if("hgjldw".equals(p.getName())){//98.海关计量单位
				hgjldw=p.getValue();
			}else if("zhwjbjldwdfms".equals(p.getName())){//99.转换为基本计量单位的分母
				zhwjbjldwdfms=p.getValue();
			}else if ("jbjldwzhfzs".equals(p.getName())) {//100.基本计量单位转换分子
				jbjldwzhfzs = p.getValue();
			}else if("ly".equals(p.getName())){//101.来源
				zx2=p.getValue();
			}else if("sfwx".equals(p.getName())){//102.是否外销
				sfwx=p.getValue();
			}else if("tkbs".equals(p.getName())){//103.特控标识
				tkbs=p.getValue();
			}else if("smfl".equals(p.getName())){//104.税目分类
				smfl=p.getValue();
			}else if("pcglls".equals(p.getName())){//105.批次管理
				pcglls=p.getValue();
			}else if("cfdd".equals(p.getName())){//106.存放地点
				cfdd=p.getValue();
			}else if("cgybh".equals(p.getName())){//107.仓管员编号
				cgybh=p.getValue();
			}else if("cctj".equals(p.getName())){//108.存储条件
				cctj=p.getValue();
			}else if("zdsyhjsmtqsxrq".equals(p.getName())){//109.最短剩余货架寿命（提前失效日期）
				zdsyhjsmtqsxrq=p.getValue();
			}else if("pgl".equals(p.getName())){//110.评估类
				pgl=p.getValue();
			}else if("jgkz".equals(p.getName())){//111.价格控制
				jgkz=p.getValue();
			}else if("jgdw".equals(p.getName())){//112.价格单位
				jgdw=p.getValue();
			}else if("jgqd".equals(p.getName())){//113.价格确定
				jgqd=p.getValue();
			}else if("hqscbgs".equals(p.getName())){//114.含QS成本估算
				hqscbgs=p.getValue();
			}else if("wlly".equals(p.getName())){//115.物料来源
				wlly=p.getValue();
			}else if("lrzx".equals(p.getName())){//116.利润中心
				lrzx=p.getValue();
			}else if("cbhspl".equals(p.getName())){//117.成本核算批量
				cbhspl=p.getValue();
			}else if("cym".equals(p.getName())){//118.差异码
				cym=p.getValue();
			}else if("khggsgysggs".equals(p.getName())){//119.客户规格书/供应商规格书
				khggsgysggs=p.getValue();
			}else if ("clmc".equals(p.getName())) {//120.材料名称
				clmc = p.getValue();
			}else if("sfmfkgcl".equals(p.getName())){//121.是否免费客供材料
				sfmfkgcl=p.getValue();
			}else if("sftxwz01".equals(p.getName())){//122.是否填写完整01
				sftxwz01=p.getValue();
			}else if("sftxwz02".equals(p.getName())){//123.是否填写完整02
				sftxwz02=p.getValue();
			}else if("sftxwz03".equals(p.getName())){//124.是否填写完整03
				sftxwz03=p.getValue();
			}else if("sftxwz04".equals(p.getName())){//125.是否填写完整04
				sftxwz04=p.getValue();
			}else if("sftxwz05".equals(p.getName())){//126.是否填写完整05
				sftxwz05=p.getValue();
			}else if("sftxwz06".equals(p.getName())){//127.是否填写完整06
				sftxwz06=p.getValue();
			}else if("sftxwz07".equals(p.getName())){//128.是否填写完整07
				sftxwz07=p.getValue();
			}else if("sftxwz08".equals(p.getName())){//129.是否填写完整08
				sftxwz08=p.getValue();
			}else if("wllx".equals(p.getName())){//130.物料类型
				wllx=p.getValue();
			}
		}
		
		RecordSet rs=new RecordSet();
		
		String workflowId=requestInfo.getWorkflowid();
		int nodeId=requestInfo.getRequestManager().getNodeid();
			
		//第1个表名
		//1.行业领域-19
		if(!this.judgeIt(gc, wllx, "xyly", workflowId, nodeId) && "".equals(xyly)){
			msg.append("行业领域为必填字段。");
		}
		//2.物料类型-130
		if(!this.judgeIt(gc, wllx, "wllx", workflowId, nodeId) && "".equals(wllx)){
			msg.append("物料类型为必填字段。");
		}
		//3.工厂-20
		if(!this.judgeIt(gc, wllx, "gc", workflowId, nodeId) && "".equals(gc)){
			msg.append("工厂为必填字段。");
		}
		//4.物料编号-22
		if(!this.judgeIt(gc, wllx, "sapwlbm", workflowId, nodeId) && "".equals(sapwlbm)){
			msg.append("物料编号为必填字段。");
		}
		//5.物料描述-24
		if(!this.judgeIt(gc, wllx, "ms", workflowId, nodeId) && "".equals(ms)){
			msg.append("物料描述为必填字段。");
		}
		//6.物料描述-英文，×
		//7.基本计量单位，×
		if(!this.judgeIt(gc, wllx, "jbdw", workflowId, nodeId) && "".equals(jbdw)){
			msg.append("基本计量单位为必填字段。");
		}
		//8.物料组-23
		if(!this.judgeIt(gc, wllx, "wlz", workflowId, nodeId) && "".equals(wlz)){
			msg.append("物料组为必填字段。");
		}
		//9.旧物料号-21
		if(!this.judgeIt(gc, wllx, "jwlh", workflowId, nodeId) && "".equals(jwlh)){
			msg.append("旧物料号为必填字段。");
		}
		//10.常规项目类别组，×
		//11.净重-36
		if(!this.judgeIt(gc, wllx, "jz", workflowId, nodeId) && "".equals(jz)){
			msg.append("净重为必填字段。");
		}
		//12.毛重-35
		if(!this.judgeIt(gc, wllx, "mz", workflowId, nodeId) && "".equals(mz)){
			msg.append("毛重为必填字段。");
		}
		//13.重量单位-37，下拉
		if(!this.judgeIt(gc, wllx, "zldw", workflowId, nodeId) && "".equals(zldw)){
			msg.append("重量单位为必填字段。");
		}
		//14.可配置物料，×
		//15.长度，×
		//16.宽度，×
		//17.高度，×
		//18.业务量-38
		if(!this.judgeIt(gc, wllx, "ywl", workflowId, nodeId) && "".equals(ywl)){
			msg.append("业务量为必填字段。");
		}
		//19.体积单位-39
		if(!this.judgeIt(gc, wllx, "tjdw", workflowId, nodeId) && "".equals(tjdw)){
			msg.append("体积单位为必填字段。");
		}
		//20.大小/量纲-40
		if(!this.judgeIt(gc, wllx, "dxlg", workflowId, nodeId) && "".equals(dxlg)){
			msg.append("大小/量纲为必填字段。");
		}
		//21.销售组织-57
		if(!this.judgeIt(gc, wllx, "xszz", workflowId, nodeId) && "".equals(xszz)){
			msg.append("销售组织为必填字段。");
		}
		//22.分销渠道-58
		if(!this.judgeIt(gc, wllx, "fxqd", workflowId, nodeId) && "".equals(fxqd)){
			msg.append("分销渠道为必填字段。");
		}
		//23.产品组-27
		if(!this.judgeIt(gc, wllx, "cpz", workflowId, nodeId) && "".equals(cpz)){
			msg.append("产品组为必填字段。");
		}
		//24.交货工厂-59
		if(!this.judgeIt(gc, wllx, "jhgc", workflowId, nodeId) && "".equals(jhgc)){
			msg.append("交货工厂为必填字段。");
		}
		//25.物料的税分类-60
		if(!this.judgeIt(gc, wllx, "sfl", workflowId, nodeId) && "".equals(sfl)){
			msg.append("物料的税分类为必填字段。");
		}
		//26.物料的科目分配组，×
		//27.物料统计组，×
		//28.运输组，×
		//29.装载组，×
		//30.采购组-61
		if(!this.judgeIt(gc, wllx, "cgz", workflowId, nodeId) && "".equals(cgz)){
			msg.append("采购组为必填字段。");
		}
		//31.采购订单计量单位，×
		if(!this.judgeIt(gc, wllx, "cgdw", workflowId, nodeId) && "".equals(cgdw)){
			msg.append("采购单位为必填字段。");
		}
		//32.基本计量单位转换分子-43
		if(!this.judgeIt(gc, wllx, "jbjldwzhfz", workflowId, nodeId) && "".equals(jbjldwzhfz)){
			msg.append("基本计量单位转换分子为必填字段。");
		}
		//33.转换为基本计量单位的分母-42
		if(!this.judgeIt(gc, wllx, "zhwjbjldwdfm", workflowId, nodeId) && "".equals(zhwjbjldwdfm)){
			msg.append("转换为基本计量单位的分母为必填字段。");
		}
		//34.可用性检查的检查组-86
		/*if(!this.judgeIt(gc, wllx, "kyxjc", workflowId, nodeId) && "".equals(kyxjc)){
			msg.append("可用性检查为必填字段。");
		}*/
		//35.批次管理需求的标识，×
		if(!this.judgeIt(gc, wllx, "pcgl", workflowId, nodeId) && "".equals(pcgl)){
			msg.append("批次管理为必填字段。");
		}else if(!this.judgeIt(gc, wllx, "pcgll", workflowId, nodeId) && "".equals(pcgll)){
			msg.append("批次管理为必填字段。");
		}else if(!this.judgeIt(gc, wllx, "pcglls", workflowId, nodeId) && "".equals(pcglls)){
			msg.append("批次管理为必填字段。");
		}
		//36.物料需求计划类型，×
		if(!this.judgeIt(gc, wllx, "mrplx", workflowId, nodeId) && "".equals(mrplx)){
			msg.append("MRP 类型为必填字段。");
		}
		//37.MRP 控制员-67
		if(!this.judgeIt(gc, wllx, "mrpkzz", workflowId, nodeId) && "".equals(mrpkzz)){
			msg.append("MRP 控制员为必填字段。");
		}
		//38.MRP 组，×
		//39.物料计划内批量程序，×
		if(!this.judgeIt(gc, wllx, "pldx", workflowId, nodeId) && "".equals(pldx)){
			msg.append("批量大小为必填字段。");
		}
		//40.最小批量-69
		if(!this.judgeIt(gc, wllx, "zxpldx", workflowId, nodeId) && "".equals(zxpldx)){
			msg.append("最小批量为必填字段。");
		}
		//41.ABC标识，×
		//42.采购订单数量的舍入值-71
		if(!this.judgeIt(gc, wllx, "srz", workflowId, nodeId) && "".equals(srz)){
			msg.append("舍入值为必填字段。");
		}
		//43.采购类型-72
		if(!this.judgeIt(gc, wllx, "cglx", workflowId, nodeId) && "".equals(cglx)){
			msg.append("采购类型为必填字段。");
		}
		//44.特殊采购类型-73
		if(!this.judgeIt(gc, wllx, "tscglx", workflowId, nodeId) && "".equals(tscglx)){
			msg.append("特殊采购类型为必填字段。");
		}
		//45.外部采购的缺省仓储位置，×
		if(!this.judgeIt(gc, wllx, "wbcgccdd", workflowId, nodeId) && "".equals(wbcgccdd)){
			msg.append("外部采购仓储地点为必填字段。");
		}
		//46.标识：反冲-76
		if(!this.judgeIt(gc, wllx, "fc", workflowId, nodeId) && "".equals(fc)){
			msg.append("反冲为必填字段。");
		}
		//47.指示符：散装物料，×
		//48.厂内生产时间，×
		if(!this.judgeIt(gc, wllx, "zzsc", workflowId, nodeId) && "".equals(zzsc)){
			msg.append("自制生产为必填字段。");
		}
		//49.计划交货时间（天）-78
		if(!this.judgeIt(gc, wllx, "cgjhjhsj", workflowId, nodeId) && "".equals(cgjhjhsj)){
			msg.append("采购计划交货时间为必填字段。");
		}
		//50.以天计的收货处理时间-79
		if(!this.judgeIt(gc, wllx, "shclsj", workflowId, nodeId) && "".equals(shclsj)){
			msg.append("收货处理时间为必填字段。");
		}
		//51.安全库存-4
		if(!this.judgeIt(gc, wllx, "aqkc", workflowId, nodeId) && "".equals(aqkc)){
			msg.append("安全库存为必填字段。");
		}
		//52.浮动排产时间容余码，×
		if(!this.judgeIt(gc, wllx, "jhbjm", workflowId, nodeId) && "".equals(jhbjm)){
			msg.append("计划边际码为必填字段。");
		}
		//53.计划策略组-81
		if(!this.judgeIt(gc, wllx, "clz", workflowId, nodeId) && "".equals(clz)){
			msg.append("策略组为必填字段。");
		}
		//54.消耗模式-82
		if(!this.judgeIt(gc, wllx, "xhms", workflowId, nodeId) && "".equals(xhms)){
			msg.append("消耗模式为必填字段。");
		}
		//55.消耗期间:逆向-83
		if(!this.judgeIt(gc, wllx, "xhxhqj", workflowId, nodeId) && "".equals(xhxhqj)){
			msg.append("消耗期间:逆向为必填字段。");
		}
		//56.消耗时期-向前-84
		if(!this.judgeIt(gc, wllx, "xqxhqj", workflowId, nodeId) && "".equals(xqxhqj)){
			msg.append("消耗时期-向前为必填字段。");
		}
		//57.综合MRP标识-85
		if(!this.judgeIt(gc, wllx, "zhmrp", workflowId, nodeId) && "".equals(zhmrp)){
			msg.append("综合MRP为必填字段。");
		}
		//58.可用性检查的检查组-86
		if(!this.judgeIt(gc, wllx, "kyxjc", workflowId, nodeId) && "".equals(kyxjc)){
			msg.append("可用性检查为必填字段。");
		}
		//59.中止指示符，×
		//60.对于独立和集中需求的相关需求标识-87，下拉
		if(!this.judgeIt(gc, wllx, "gbjz", workflowId, nodeId) && "".equals(gbjz)){
			msg.append("个别/集中为必填字段。");
		}
		//61.中断日期，×
		//62.后续物料，×
		//63.发货单位，×
		//64.基本计量单位转换分子-100
		if(!this.judgeIt(gc, wllx, "jbjldwzhfzs", workflowId, nodeId) && "".equals(jbjldwzhfzs)){
			msg.append("基本计量单位转换分子为必填字段。");
		}
		//65.转换为基本计量单位的分母-99
		if(!this.judgeIt(gc, wllx, "zhwjbjldwdfms", workflowId, nodeId) && "".equals(zhwjbjldwdfms)){
			msg.append("转换为基本计量单位的分母为必填字段。");
		}
		//66.生产管理员-88
		if(!this.judgeIt(gc, wllx, "scgly", workflowId, nodeId) && "".equals(scgly)){
			msg.append("生产管理员为必填字段。");
		}
		//67.生产计划参数文件-89
		if(!this.judgeIt(gc, wllx, "scjhcswj", workflowId, nodeId) && "".equals(scjhcswj)){
			msg.append("生产计划参数文件为必填字段。");
		}
		//68.最短剩余货架寿命-109
		if(!this.judgeIt(gc, wllx, "zdsyhjsmtqsxrq", workflowId, nodeId) && "".equals(zdsyhjsmtqsxrq)){
			msg.append("最短剩余货架寿命为必填字段。");
		}
		//69.总货架寿命，×
		//70.货架寿命到期日的期间标识，×
		//71.激活采购中的QM，×
		//72.采购中质量管理的控制码，×
		//73.评估类-110
		if(!this.judgeIt(gc, wllx, "pgl", workflowId, nodeId) && "".equals(pgl)){
			msg.append("评估类为必填字段。");
		}
		//74.物料价格确定：控制-111
		if(!this.judgeIt(gc, wllx, "jgqd", workflowId, nodeId) && "".equals(jgqd)){
			msg.append("价格确定为必填字段。");
		}
		//75.价格控制指示符，×
		if(!this.judgeIt(gc, wllx, "jgkz", workflowId, nodeId) && "".equals(jgkz)){
			msg.append("价格控制为必填字段。");
		}
		//76.价格单位-112
		if(!this.judgeIt(gc, wllx, "jgdw", workflowId, nodeId) && "".equals(jgdw)){
			msg.append("价格单位为必填字段。");
		}
		//77.物料根据数量结构进行成本核算，×
		if(!this.judgeIt(gc, wllx, "hqscbgs", workflowId, nodeId) && "".equals(hqscbgs)){
			msg.append("含QS成本估算为必填字段。");
		}
		//78.物料相关的源，×
		if(!this.judgeIt(gc, wllx, "lyy", workflowId, nodeId) && "".equals(lyy)){
			msg.append("物料相关的源为必填字段。");
		}else if(!this.judgeIt(gc, wllx, "wlly", workflowId, nodeId) && "".equals(wlly)){
			msg.append("物料相关的源为必填字段。");
		}
		//79.利润中心-116
		if(!this.judgeIt(gc, wllx, "lrzx", workflowId, nodeId) && "".equals(lrzx)){
			msg.append("利润中心为必填字段。");
		}
		//80.无成本核算，×
		//81.批量产品成本核算-117
		if(!this.judgeIt(gc, wllx, "cbhspl", workflowId, nodeId) && "".equals(cbhspl)){
			msg.append("成本核算批量为必填字段。");
		}
		//82.库存地点，×
		if(!this.judgeIt(gc, wllx, "scccdd", workflowId, nodeId) && "".equals(scccdd)){
			msg.append("生产仓储地点为必填字段。");
		}
		if(!this.judgeIt(gc, wllx, "yqd", workflowId, nodeId) && "".equals(yqd)){
			msg.append("源清单为必填字段。");
		}
		if(!this.judgeIt(gc, wllx, "cctj", workflowId, nodeId) && "".equals(cctj)){
			msg.append("存储条件为必填字段。");
		}
		if(!this.judgeIt(gc, wllx, "cym", workflowId, nodeId) && "".equals(cym)){
			msg.append("差异码为必填字段。");
		}
		if(!this.judgeIt(gc, wllx, "cgjzdm", workflowId, nodeId) && "".equals(cgjzdm)){
			msg.append("采购价值代码为必填字段。");
		}
		if(!this.judgeIt(gc, wllx, "zdpldx", workflowId, nodeId) && "".equals(zdpldx)){
			msg.append("最大批量大小为必填字段。");
		}
		//第2个表名
		//1.集团
		//2.物料号码，无搜索帮助-22
		if(!this.judgeIt(gc, wllx, "sapwlbm", workflowId, nodeId) && "".equals(sapwlbm)){
			msg.append("物料号码为必填字段。");
		}
		//3.标准外箱包装数量-41
		if(!this.judgeIt(gc, wllx, "bzwxbzsl", workflowId, nodeId) && "".equals(bzwxbzsl)){
			msg.append("标准外箱包装数量为必填字段。");
		}
		//4.内箱包装数量-53
		if(!this.judgeIt(gc, wllx, "nxbzsl", workflowId, nodeId) && "".equals(nxbzsl)){
			msg.append("内箱包装数量为必填字段。");
		}
		//5.层/包/盒包装数量-54
		if(!this.judgeIt(gc, wllx, "cbhbzsl", workflowId, nodeId) && "".equals(cbhbzsl)){
			msg.append("层/包/盒包装数量为必填字段。");
		}
		//6.客户代码-28
		if(!this.judgeIt(gc, wllx, "khdm", workflowId, nodeId) && "".equals(khdm)){
			msg.append("客户代码为必填字段。");
		}
		//7.客户物料编码-29
		if(!this.judgeIt(gc, wllx, "khwlbm", workflowId, nodeId) && "".equals(khwlbm)){
			msg.append("客户物料编码为必填字段。");
		}
		//8.制造商编码-30
		if(!this.judgeIt(gc, wllx, "zzsbm", workflowId, nodeId) && "".equals(zzsbm)){
			msg.append("制造商编码为必填字段。");
		}
		//9.制造商-31
		if(!this.judgeIt(gc, wllx, "zzs", workflowId, nodeId) && "".equals(zzs)){
			msg.append("制造商为必填字段。");
		}
		//10.供应商，×
		//tables.setValue("ZGYS", "THK1");
		//11.属性1-44
		if(!this.judgeIt(gc, wllx, "zx1", workflowId, nodeId) && "".equals(zx1)){
			msg.append("属性1为必填字段。");
		}
		//12.属性2-45
		if(!this.judgeIt(gc, wllx, "zx2", workflowId, nodeId) && "".equals(zx2)){
			msg.append("属性2为必填字段。");
		}
		//13.属性3-46
		if(!this.judgeIt(gc, wllx, "zx3", workflowId, nodeId) && "".equals(zx3)){
			msg.append("属性3为必填字段。");
		}
		//14.报关属性1，×
		if(!this.judgeIt(gc, wllx, "bgzx3", workflowId, nodeId) && "".equals(bgzx3)){
			msg.append("报关属性1为必填字段。");
		}
		//15.报关属性2，×
		if(!this.judgeIt(gc, wllx, "bgzx4", workflowId, nodeId) && "".equals(bgzx4)){
			msg.append("报关属性2为必填字段。");
		}
		//16.报关属性3-电压-47
		if(!this.judgeIt(gc, wllx, "bgzx1", workflowId, nodeId) && "".equals(bgzx1)){
			msg.append("报关属性3-电压为必填字段。");
		}
		//17.报关属性4-品牌-48
		if(!this.judgeIt(gc, wllx, "bgzx2", workflowId, nodeId) && "".equals(bgzx2)){
			msg.append("报关属性4-品牌为必填字段。");
		}
		//18.样品号-51
		if(!this.judgeIt(gc, wllx, "yph", workflowId, nodeId) && "".equals(yph)){
			msg.append("样品号为必填字段。");
		}
		//19.是否车载-52
		if(!this.judgeIt(gc, wllx, "sfcz", workflowId, nodeId) && "".equals(sfcz)){
			msg.append("是否车载为必填字段。");
		}
		//20.图纸号-33
		if(!this.judgeIt(gc, wllx, "tzbh", workflowId, nodeId) && "".equals(tzbh)){
			msg.append("图纸号为必填字段。");
		}
		//21.图纸版本-34
		if(!this.judgeIt(gc, wllx, "bbh", workflowId, nodeId) && "".equals(bbh)){
			msg.append("图纸版本为必填字段。");
		}
		
		//第3个表名
		//1.集团
		//2.物料号码，无搜索帮助-22
		if(!this.judgeIt(gc, wllx, "sapwlbm", workflowId, nodeId) && "".equals(sapwlbm)){
			msg.append("物料号码为必填字段。");
		}
		//3.工厂-20
		if(!this.judgeIt(gc, wllx, "gc", workflowId, nodeId) && "".equals(gc)){
			msg.append("工厂为必填字段。");
		}
		//4.报关编码-96
		if(!this.judgeIt(gc, wllx, "bgbm", workflowId, nodeId) && "".equals(bgbm)){
			msg.append("报关编码为必填字段。");
		}
		//5.报关名称-97
		if(!this.judgeIt(gc, wllx, "bgmc", workflowId, nodeId) && "".equals(bgmc)){
			msg.append("报关名称为必填字段。");
		}
		//6.海关计量单位-98
		if(!this.judgeIt(gc, wllx, "hgjldw", workflowId, nodeId) && "".equals(hgjldw)){
			msg.append("海关计量单位为必填字段。");
		}
		//7.转换为基本计量单位的分母-99
		if(!this.judgeIt(gc, wllx, "zhwjbjldwdfms", workflowId, nodeId) && "".equals(zhwjbjldwdfms)){
			msg.append("转换为基本计量单位的分母为必填字段。");
		}
		//8.基本计量单位转换分子-100
		if(!this.judgeIt(gc, wllx, "jbjldwzhfzs", workflowId, nodeId) && "".equals(jbjldwzhfzs)){
			msg.append("基本计量单位转换分子为必填字段。");
		}
		//9.来源-101，下拉
		if(!this.judgeIt(gc, wllx, "ly", workflowId, nodeId) && "".equals(ly)){
			msg.append("来源为必填字段。");
		}
		//10.是否外销-102
		if(!this.judgeIt(gc, wllx, "sfwx", workflowId, nodeId) && "".equals(sfwx)){
			msg.append("是否外销为必填字段。");
		}
		//11.特控标识-103
		if(!this.judgeIt(gc, wllx, "tkbs", workflowId, nodeId) && "".equals(tkbs)){
			msg.append("特控标识为必填字段。");
		}
		//12.税目分类-104
		if(!this.judgeIt(gc, wllx, "smfl", workflowId, nodeId) && "".equals(smfl)){
			msg.append("税目分类为必填字段。");
		}
		//13.存放地点-106
		if(!this.judgeIt(gc, wllx, "cfdd", workflowId, nodeId) && "".equals(cfdd)){
			msg.append("存放地点为必填字段。");
		}
		//14.仓管员编号-107
		if(!this.judgeIt(gc, wllx, "cgybh", workflowId, nodeId) && "".equals(cgybh)){
			msg.append("仓管员编号为必填字段。");
		}
		//15.无卤-91
		if(!this.judgeIt(gc, wllx, "wl", workflowId, nodeId) && "".equals(wl)){
			msg.append("无卤为必填字段。");
		}
		//16.ROHS2.0-92
		if(!this.judgeIt(gc, wllx, "rohs20", workflowId, nodeId) && "".equals(rohs20)){
			msg.append("ROHS2.0为必填字段。");
		}
		//17.特别检查标识-93
		if(!this.judgeIt(gc, wllx, "tbjcbs", workflowId, nodeId) && "".equals(tbjcbs)){
			msg.append("特别检查标识为必填字段。");
		}
		//18.环境等级-94，下拉
		if(!this.judgeIt(gc, wllx, "hjdj", workflowId, nodeId) && "".equals(hjdj)){
			msg.append("环境等级为必填字段。");
		}
		//19.不含邻苯二甲酸酯(6P)-95
		if(!this.judgeIt(gc, wllx, "bhlbejsz6p", workflowId, nodeId) && "".equals(bhlbejsz6p)){
			msg.append("不含邻苯二甲酸酯(6P)为必填字段。");
		}
		//10.供应商，从上表挪动-32
		if(!this.judgeIt(gc, wllx, "gys", workflowId, nodeId) && "".equals(gys)){
			msg.append("供应商为必填字段。");
		}
		
		RequestManager requestManager = requestInfo.getRequestManager();
		if("".equals(msg.toString())){//返回成功
			flag="1";
			rs.writeLog("校验成功！"+msg.toString());
		}else{//返回异常
			flag="0";
			requestManager.setMessageid("111000");
			msg.append(xinx);
			msg.insert(0, "很抱歉，提交失败。原因如下：<br/>");
			requestManager.setMessagecontent(msg.toString());
		}
		
		return flag;
	}
	
	/**
	 * 查询此工厂、此物料类型此字段是否存在，存在则false
	 * @param gongc 工厂
	 * @param wullx 物料类型
	 * @param zid 字段
	 * @return
	 */
	private boolean judgeIt(String gongc,String wullx,String zid,String workflowId,int nodeId){
		boolean flag=true;
		RecordSet rs=new RecordSet();
		String sql="select zid from uf_wy0817 where gongc='"+gongc+"' and wullx='"+wullx+"'";
		int count=-1;
		flag=rs.execute(sql);
		String sqls="";
		String zids="";
		if(flag){
			while(rs.next()){
				zids=rs.getString("zid");
				String [] str=zids.split(",");
				for (String string : str) {
					if(zid.equals(string)){
						count=1;
					}
				}
				if(count>0){//存在
					//判断当前节点是否可编辑
					RecordSet rss=new RecordSet();
					//修改的workflowid-78，创建的workflowid-50
					sqls="select count(1) as tt from workflow_nodeform"
							//可编辑
							+" where isedit='1'"
							//字段名
							+" and fieldid in (select id from workflow_billfield"
							+" where billid=(select formid from workflow_base where id ='"+workflowId+"')"
						    +" and (detailtable is NULL OR detailtable='')"
							+" and fieldname='"+zid+"')"
							//节点ID
						    +" and nodeid='"+nodeId+"'";
					boolean flags=rss.execute(sqls);
					rss.writeLog("可编辑查询："+sqls);
					int counts=-1;
					if(flags){
						while(rss.next()){
							counts=rss.getInt("tt")<0?0:rss.getInt("tt");
							if(counts>0){
								flag=false;
							}
						}
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 更新表单信息
	 * @param rq
	 * @param fanhxx
	 * @return
	 */
	private boolean updateForm(RequestInfo rq,String fanhxx) {
		int formid = rq.getRequestManager().getFormid();
		formid = Math.abs(formid);  
		String requestid = rq.getRequestid();
		RecordSet rs = new RecordSet();
		String sql = "update formtable_main_"+formid+" set pzsbxx='"+fanhxx+"' where requestid='"+requestid+"'";
		return rs.execute(sql);
	}
	
}
