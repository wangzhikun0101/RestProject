package com.westvalley.g.materiel.create;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import com.weaver.general.Util;
import com.westvalley.g.materiel.GUtil;
import com.westvalley.g.sap.SAPCall;
import weaver.conn.RecordSet;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.action.BaseAction;
import weaver.workflow.request.RequestManager;

/**
 * 功能：调用创建/修改接口
 */

public class CreateMasterData extends BaseAction {

	/**
	 * 操作：
	 * 		回写成败相关信息
	 */
	@Override
	public String execute(RequestInfo requestInfo) {
		String flag="1";
		// 获取流程主表信息
		Property[] properties = requestInfo.getMainTableInfo().getProperty();
		
		String wlxl="";//物料细类
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
		String pcglboss="";
		String lyboss="";
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
		String zhjsmjyzq="";
		String sjdw="";
		
		String tscglx1="";//特殊采购类型1
		String wlxqjhlx1="";//物料需求计划类型1
		String wbcgccdd1="";//外部采购仓储地点1
		
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
			}else if("wlbianhao".equals(p.getName())){//22.SAP物料编码
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
				ly=p.getValue();
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
			}else if("wlxl".equals(p.getName())){//物料细类
				wlxl=p.getValue();
			}else if("zhjsmjyzq".equals(p.getName())){//
				zhjsmjyzq=p.getValue();
			}else if("sjdw".equals(p.getName())){//
				sjdw=p.getValue();
			}else if("cgddjldw".equals(p.getName())){//
				cgdw=p.getValue();
			}else if("tscglx1".equals(p.getName())){
				tscglx1=p.getValue();
			}else if("wlxqjhlx1".equals(p.getName())){
				wlxqjhlx1=p.getValue();
			}else if("wbcgccdd1".equals(p.getName())){
				wbcgccdd1=p.getValue();
			}
		}
		
		RecordSet rs=new RecordSet();
		try {
			SAPCall call = new SAPCall();	
			JCoFunction function = call.getFunction("ZRFC_MATERIAL_CREATE");//创建函数	
		
			//赋值表头信息
			JCoParameterList importParameterList = function.getImportParameterList();
			
			JCoParameterList tableParameterList = function.getTableParameterList();
			
			//第1个表名
			JCoTable table = tableParameterList.getTable("IT_TAB");//获取表名
			table.appendRows(1);
			table.setRow(0);
			int i=0;
			//1.行业领域-19
			table.setValue("MBRSH", xyly);
			rs.writeLog("xyly："+xyly);
			//2.物料类型-130
			GUtil gUtil=new GUtil();
			wllx=gUtil.huoQWuLiaoLeiXing(wllx);
			table.setValue("MATL_TYPE", wllx);
			rs.writeLog("wllx："+wllx);
			//3.工厂-20
			table.setValue("PLANT", gc);
			rs.writeLog("gc："+gc);
			//4.物料编号-22
			table.setValue("MATNR", sapwlbm);
			rs.writeLog("sapwlbm："+sapwlbm);
			//5.物料描述-24
			table.setValue("MATL_DESC", ms.replaceAll("<br/>", "")
										  .replaceAll("<br>", "")
										  .replaceAll("&#60;br&#62;", " ")
										  
										  .replaceAll("&nbsp;", " ")
										  .replaceAll("&#38;nbsp;", " ")
										  
										  .replaceAll("&quot;", "\"")
										  .replaceAll("&#38;quot;", "\"")
										  
										  .replaceAll("&gt;", ">")
										  .replaceAll("&#38;gt;", ">")
										  
										  .replaceAll("&#38;lt;", "<")
										  .replaceAll("&lt;", "<"));
			rs.writeLog("ms："+ms);
			//6.物料描述-英文，×
			table.setValue("MATL_DESC_EN", "");
			rs.writeLog("MATL_DESC_EN：");
			//7.基本计量单位-26
			table.setValue("BASE_UOM", jbdw);
			rs.writeLog("jbdw："+jbdw);
			//8.物料组-23
			table.setValue("MATL_GROUP", wlz);
			rs.writeLog("wlz："+wlz);
			//ZMTXL
			table.setValue("ZMTXL", wlxl);
			rs.writeLog("wlxl："+wlxl);
			//9.旧物料号-21
			table.setValue("BISMT", jwlh);
			rs.writeLog("jwlh："+jwlh);
			//10.常规项目类别组，×
			table.setValue("MTPOS_MARA", "");
			rs.writeLog("MTPOS_MARA：");
			//11.净重-36
			table.setValue("NET_WEIGHT", jz);
			rs.writeLog("jz："+jz);
			//12.毛重-35
			table.setValue("GROSS_WT", mz);
			rs.writeLog("mz："+mz);
			//13.重量单位-37，下拉
			if("0".equals(zldw)){
				zldw="MG";
			}else if("1".equals(zldw)){
				zldw="G";
			}else if("2".equals(zldw)){
				zldw="KG";
			}
			table.setValue("UNIT_OF_WT", zldw);
			rs.writeLog("zldw："+zldw);
			//14.可配置物料，×
			table.setValue("KZKFG", "");
			rs.writeLog("KZKFG：");
			//15.长度，×
			table.setValue("LENGTH", "");
			rs.writeLog("LENGTH：");
			//16.宽度，×
			table.setValue("WIDTH", "");
			rs.writeLog("WIDTH：");
			//17.高度，×
			table.setValue("HEIGHT", "");
			rs.writeLog("HEIGHT：");
			//18.业务量-38
			table.setValue("VOLUM", ywl);
			rs.writeLog("ywl："+ywl);
			//19.体积单位-39
			table.setValue("VOLEH", tjdw);
			rs.writeLog("tjdw："+tjdw);
			//20.大小/量纲-40
			table.setValue("SIZE_DIM", dxlg);
			//21.销售组织-57
			table.setValue("VKORG", xszz);
			rs.writeLog("xszz："+xszz);
			//22.分销渠道-58
			table.setValue("VTWEG", fxqd);
			rs.writeLog("fxqd："+fxqd);
			//23.产品组-27
			table.setValue("SPART", cpz);
			rs.writeLog("cpz："+cpz);
			//24.交货工厂-59
			table.setValue("DWERK", jhgc);
			rs.writeLog("jhgc："+jhgc);
			//25.物料的税分类-60
			table.setValue("TAXCLASS", sfl);
			rs.writeLog("sfl："+sfl);
			//26.物料的科目分配组，×
			table.setValue("KTGRM", "");
			rs.writeLog("KTGRM：");
			//27.物料统计组，×
			table.setValue("STGMA", "");
			rs.writeLog("STGMA：");
			//28.运输组，×
			table.setValue("TRAGR", "");
			rs.writeLog("TRAGR：");
			//29.装载组，×
			table.setValue("LADGR", "");
			rs.writeLog("LADGR：");
			//30.采购组-61
			table.setValue("EKGRP", cgz);
			rs.writeLog("cgz："+cgz);
			//31.采购订单计量单位，×
			table.setValue("BSTME", cgdw);
			rs.writeLog("cgdw："+cgdw);
			//32.基本计量单位转换分子-43
			table.setValue("UMREZ", jbjldwzhfzs);//采购
			rs.writeLog("jbjldwzhfzs："+jbjldwzhfzs);
			//33.转换为基本计量单位的分母-42
			table.setValue("UMREN", zhwjbjldwdfms);//采购
			rs.writeLog("zhwjbjldwdfms："+zhwjbjldwdfms);
			//34.可用性检查的检查组-86
			if("0".equals(kyxjc)){
				kyxjc="01";
			}else if("1".equals(kyxjc)){
				kyxjc="02";
			}
			table.setValue("MTVFP", kyxjc);
			rs.writeLog("kyxjc："+kyxjc);
			//35.批次管理需求的标识，×
			if(!"".equals(pcgl)){
				pcglboss=pcgl;
			}else if(!"".equals(pcgll)){
				pcglboss=pcgll;
			}else if(!"".equals(pcglls)){
				pcglboss=pcglls;
			}
			if("0".equals(pcglboss)){
				pcglboss="X";
			}else{
				pcglboss="";
			}
			if("0".equals(pcgl) || "0".equals(pcgll) || "0".equals(pcglls)){
				pcglboss="X";
			}
			table.setValue("XCHPF", pcglboss);
			rs.writeLog("pcgl："+pcgl);
			rs.writeLog("pcglboss："+pcglboss);
			//36.物料需求计划类型，×
			if(!"".equals(wlxqjhlx1)){
				if("0".equals(wlxqjhlx1)){
					wlxqjhlx1="ND";
				}else if("1".equals(wlxqjhlx1)){
					wlxqjhlx1="PD";
				}else if("2".equals(wlxqjhlx1)){
					wlxqjhlx1="VB";
				}
				table.setValue("DISMM", wlxqjhlx1);
				rs.writeLog("wlxqjhlx1："+wlxqjhlx1);
			}else if(!"".equals(mrplx)){//旧表单字段
				table.setValue("DISMM", mrplx);
				rs.writeLog("mrplx："+mrplx);
			}
			//37.MRP 控制员-67
			table.setValue("DISPO", mrpkzz);
			rs.writeLog("mrpkzz："+mrpkzz);
			//38.MRP 组，×
			table.setValue("DISGR", "");
			rs.writeLog("DISGR：");
			//39.物料计划内批量程序，×
			if("0".equals(pldx)){
				pldx="EX";
			}else if("1".equals(pldx)){
				pldx="TB";
			}else if("2".equals(pldx)){
				pldx="W2";
			}else if("3".equals(pldx)){
				pldx="WB";
			}
			table.setValue("DISLS", pldx);
			rs.writeLog("pldx："+pldx);
			//40.最小批量-69
			table.setValue("BSTMI", zxpldx);
			rs.writeLog("zxpldx："+zxpldx);
			//41.ABC标识，×
			table.setValue("ABC_ID", "");
			rs.writeLog("ABC_ID：");
			//42.采购订单数量的舍入值-71
			table.setValue("BSTRF", srz);
			rs.writeLog("srz："+srz);
			//43.采购类型-72
			if("0".equals(cglx)){
				cglx="E";
			}else if("1".equals(cglx)){
				cglx="F";
			}else if("2".equals(cglx)){
				cglx="X";
			}else{
				cglx="";
			}
			table.setValue("BESKZ", cglx);
			rs.writeLog("cglx："+cglx);
			//44.特殊采购类型-73
			if(!"".equals(tscglx1)){
				if("0".equals(tscglx1)){
					tscglx1="10";
				}else if("1".equals(tscglx1)){
					tscglx1="30";
				}else if("2".equals(tscglx1)){
					tscglx1="45";
				}else if("3".equals(tscglx1)){
					tscglx1="50";
				}else if("4".equals(tscglx1)){
					tscglx1="52";
				}
				table.setValue("SOBSL", tscglx1);
				rs.writeLog("tscglx1："+tscglx1);
			}else if(!"".equals(tscglx)){//文本框，旧表单
				table.setValue("SOBSL", tscglx);
				rs.writeLog("tscglx："+tscglx);
			}
			//45.外部采购的缺省仓储位置，×
			if(!"".equals(wbcgccdd1)){
				if("0".equals(wbcgccdd1)){
					wbcgccdd1="NYDJ";
				}else if("1".equals(wbcgccdd1)){
					wbcgccdd1="WYDJ";
				}
				table.setValue("LGFSB", wbcgccdd1);
				rs.writeLog("wbcgccdd1："+wbcgccdd1);
			}else if(!"".equals(wbcgccdd)){
				table.setValue("LGFSB", wbcgccdd);
				rs.writeLog("wbcgccdd："+wbcgccdd);
			}
			//46.标识：反冲-76
			table.setValue("RGEKZ", fc);
			rs.writeLog("fc："+fc);
			//47.指示符：散装物料，×
			table.setValue("SCHGT", "");
			rs.writeLog("SCHGT：");
			//48.厂内生产时间，×
			table.setValue("DZEIT", zzsc);
			rs.writeLog("zzsc："+zzsc);
			//49.计划交货时间（天）-78，MRP视图
			table.setValue("PLIFZ", cgjhjhsj);
			rs.writeLog("cgjhjhsj："+cgjhjhsj);
			//50.以天计的收货处理时间-79，采购视图
			table.setValue("WEBAZ", shclsj);
			rs.writeLog("shclsj："+shclsj);
			//51.安全库存-4
			table.setValue("EISBE", aqkc);
			rs.writeLog("aqkc："+aqkc);
			//52.浮动排产时间容余码，×
			table.setValue("FHORI", jhbjm);
			rs.writeLog("jhbjm："+jhbjm);
			//53.计划策略组-81
			if("0".equals(clz)){
				clz="Z1";
			}else if("1".equals(clz)){
				clz="40";
			}else if("2".equals(clz)){
				clz="70";
			}else if("3".equals(clz)){
				clz="50";
			}
			table.setValue("STRGR", clz);
			rs.writeLog("clz："+clz);
			//54.消耗模式-82
			table.setValue("VRMOD", xhms);
			rs.writeLog("xhms："+xhms);
			//55.消耗期间:逆向-83
			table.setValue("VINT1", xhxhqj);
			rs.writeLog("xhxhqj："+xhxhqj);
			//56.消耗时期-向前-84
			table.setValue("VINT2", xqxhqj);
			rs.writeLog("xqxhqj："+xqxhqj);
			//57.综合MRP标识-85
			table.setValue("MISKZ", zhmrp);
			rs.writeLog("zhmrp："+zhmrp);
			//58.可用性检查的检查组-86
			if("0".equals(kyxjc)){
				kyxjc="01";
			}else if("1".equals(kyxjc)){
				kyxjc="02";
			}
			table.setValue("MTVFP1", kyxjc);
			rs.writeLog("kyxjc："+kyxjc);
			//59.中止指示符，×
			table.setValue("KZAUS", "");
			rs.writeLog("KZAUS：");
			//60.对于独立和集中需求的相关需求标识-87，下拉
			if("0".equals(gbjz)){
				gbjz="1";
			}else if("1".equals(gbjz)){
				gbjz="2";
			}
			table.setValue("SBDKZ", gbjz);
			rs.writeLog("gbjz："+gbjz);
			//61.中断日期，×
			table.setValue("AUSDT", "");
			rs.writeLog("AUSDT：");
			//62.后续物料，×
			table.setValue("NFMAT", "");
			rs.writeLog("NFMAT：");
			//63.发货单位，×
			table.setValue("AUSME", "");
			rs.writeLog("AUSME：");
			//64.基本计量单位转换分子-100
			table.setValue("UMREZ1", "");//不传s
			rs.writeLog("UMREZ1：");
			//65.转换为基本计量单位的分母-99
			table.setValue("UMREN1", "");
			rs.writeLog("UMREN1：");
			//66.生产管理员-88
			scgly=this.getCodeById(scgly);
			table.setValue("FEVOR", scgly);
			rs.writeLog("scgly："+scgly);
			//67.生产计划参数文件-89
			table.setValue("CO_PRODPRF", scjhcswj);
			rs.writeLog("scjhcswj："+scjhcswj);
			//68.最短剩余货架寿命-109
			//if("0".equals(zdsyhjsmtqsxrq)){
				//zdsyhjsmtqsxrq="X";
				table.setValue("MHDRZ", zdsyhjsmtqsxrq);
			//}else{
				//zdsyhjsmtqsxrq="";
				table.setValue("MHDRZ", zdsyhjsmtqsxrq);
			//}
			rs.writeLog("zdsyhjsmtqsxrq："+zdsyhjsmtqsxrq);
			//69.总货架寿命，×
			table.setValue("MHDHB", zhjsmjyzq);
			rs.writeLog("zhjsmjyzq："+zhjsmjyzq);
			//70.货架寿命到期日的期间标识，×
			table.setValue("DATTP", "");
			rs.writeLog("DATTP：");
			//71.激活采购中的QM，×
			table.setValue("QMPUR", "");
			rs.writeLog("QMPUR：");
			//72.采购中质量管理的控制码，×
			table.setValue("SSQSS", "");
			rs.writeLog("SSQSS：");
			//73.评估类-110
			table.setValue("BKLAS", pgl);
			rs.writeLog("pgl："+pgl);
			//74.物料价格确定：控制-111
			table.setValue("MLAST", jgqd);
			rs.writeLog("jgqd："+jgqd);
			//75.价格控制指示符，×
			if("0".equals(jgkz)){
				jgkz="V";
			}else if("1".equals(jgkz)){
				jgkz="S";
			}
			table.setValue("VPRSV", jgkz);
			rs.writeLog("jgkz："+jgkz);
			//76.价格单位-112
			table.setValue("PEINH", jgdw);
			rs.writeLog("jgdw："+jgdw);
			//77.物料根据数量结构进行成本核算，×
			if("0".equals(hqscbgs)){
				hqscbgs="X";
			}else if("1".equals(hqscbgs)){
				hqscbgs="";
			}
			table.setValue("EKALR", hqscbgs);
			//78.物料相关的源，×
			if(!"".equals(wlly)){
				if("0".equals(wlly)){
					wlly="X";
				}else if("1".equals(wlly)){
					wlly="";
				}
			}
			//rs.writeLog("来源1："+ly+"，来源2："+wlly);
			table.setValue("HKMAT", wlly);
			rs.writeLog("wlly："+wlly);
			//79.利润中心-116
			table.setValue("PRCTR", lrzx);
			rs.writeLog("lrzx："+lrzx);
			//80.无成本核算，×
			table.setValue("NCOST", "");
			rs.writeLog("NCOST：");
			//81.批量产品成本核算-117
			table.setValue("LOSGR", cbhspl);
			rs.writeLog("cbhspl："+cbhspl);
			//82.库存地点，×
			table.setValue("LGORT", scccdd);
			rs.writeLog("scccdd："+scccdd);
			//83.源清单，×
			if("0".equals(yqd)){
				yqd="X";
			}else{
				yqd="";
			}
			table.setValue("KORDB", yqd);
			rs.writeLog("yqd："+yqd);
			//84.存储条件，×
			if("0".equals(cctj)){
				cctj="01";
			}else if("1".equals(cctj)){
				cctj="02";
			}else if("2".equals(cctj)){
				cctj="03";
			}
			table.setValue("TEMPB", cctj);
			rs.writeLog("cctj："+cctj);
			//85.差异码，×
			table.setValue("AWSLS", cym);
			rs.writeLog("cym："+cym);
			//86.采购价值代码，×
			table.setValue("EKWSL", cgjzdm);
			rs.writeLog("cgjzdm："+cgjzdm);
			//87.最大批量大小，×
			table.setValue("BSTMA", zdpldx);
			rs.writeLog("zdpldx："+zdpldx);
			//INSMK
			if("0".equals(pcfl)){
				pcfl="ZBATCH_YCL";
				table.setValue("CLASS", pcfl);
			}else if("1".equals(pcfl)){
				pcfl="ZBATCH_CP";
				table.setValue("CLASS", pcfl);
			}else if("2".equals(pcfl)){
				pcfl="ZBATCH_FM";
				table.setValue("CLASS", pcfl);
			}
			rs.writeLog("pcfl："+pcfl);
			table.setValue("KLART", pclx);
			rs.writeLog("pclx："+pclx);
			table.setValue("LZEIH", sjdw);
			rs.writeLog("sjdw："+sjdw);
			
			//采购单文本，add by ganyj 2019-06-13
			table.setValue("TEXT03", cgdwb.replaceAll("<br/>", "")
					  .replaceAll("<br>", "")
					  .replaceAll("&#60;br&#62;", " ")
					  
					  .replaceAll("&nbsp;", " ")
					  .replaceAll("&#38;nbsp;", " ")
					  
					  .replaceAll("&quot;", "\"")
					  .replaceAll("&#38;quot;", "\"")
					  
					  .replaceAll("&gt;", ">")
					  .replaceAll("&#38;gt;", ">")
					  
					  .replaceAll("&#38;lt;", "<")
					  .replaceAll("&lt;", "<"));
			rs.writeLog("cgdwb："+cgdwb);
			//物料文本，add by ganyj 2019-06-13
			table.setValue("MAKTXLONG", wlwb.replaceAll("<br/>", "")
					  .replaceAll("<br>", "")
					  .replaceAll("&#60;br&#62;", " ")
					  
					  .replaceAll("&nbsp;", " ")
					  .replaceAll("&#38;nbsp;", " ")
					  
					  .replaceAll("&quot;", "\"")
					  .replaceAll("&#38;quot;", "\"")
					  
					  .replaceAll("&gt;", ">")
					  .replaceAll("&#38;gt;", ">")
					  
					  .replaceAll("&#38;lt;", "<")
					  .replaceAll("&lt;", "<"));
			rs.writeLog("wlwb："+wlwb);
			
			//第2个表名
			JCoTable tables = tableParameterList.getTable("ZMARA_DATA");//获取表名
			tables.appendRows(1);
			tables.setRow(0);
			//1.集团
			tables.setValue("MANDT", "800");
			//2.物料号码，无搜索帮助-22
			tables.setValue("MATNR", sapwlbm);
			rs.writeLog("sapwlbm："+sapwlbm);
			//3.标准外箱包装数量-41
			tables.setValue("ZWXBZ", bzwxbzsl);
			rs.writeLog("bzwxbzsl："+bzwxbzsl);
			//4.内箱包装数量-53
			tables.setValue("ZNXBZ", nxbzsl);
			rs.writeLog("nxbzsl："+nxbzsl);
			//5.层/包/盒包装数量-54
			tables.setValue("ZZXBZ", cbhbzsl);
			rs.writeLog("cbhbzsl："+cbhbzsl);
			//6.客户代码-28
			tables.setValue("ZKHDM", khdm);
			rs.writeLog("khdm："+khdm);
			//7.客户物料编码-29
			tables.setValue("ZKHWLH", khwlbm);
			rs.writeLog("khwlbm："+khwlbm);
			//8.制造商编码-30
			tables.setValue("ZZZSBM", zzsbm);
			rs.writeLog("zzsbm："+zzsbm);
			//9.制造商-31
			tables.setValue("ZZZS", zzs);
			rs.writeLog("zzs："+zzs);
			//10.供应商，×
			//tables.setValue("ZGYS", "THK1");
			//11.属性1-44
			tables.setValue("ZSX1", zx1);
			rs.writeLog("zx1："+zx1);
			//12.属性2-45
			tables.setValue("ZSX2", zx2);
			rs.writeLog("zx2："+zx2);
			//13.属性3-46
			tables.setValue("ZSX3", zx3);
			rs.writeLog("zx3："+zx3);
			//14.报关属性1，×
			tables.setValue("ZBGSX1", bgzx3);
			rs.writeLog("bgzx3："+bgzx3);
			//15.报关属性2，×
			tables.setValue("ZBGSX2", bgzx4);
			rs.writeLog("bgzx4："+bgzx4);
			//16.报关属性3-电压-47
			tables.setValue("ZBGSX3_DY", bgzx1);
			rs.writeLog("bgzx1："+bgzx1);
			//17.报关属性4-品牌-48
			tables.setValue("ZBGSX4_PINP", bgzx2);
			rs.writeLog("bgzx2："+bgzx2);
			//18.样品号-51
			tables.setValue("ZYPH", yph);
			rs.writeLog("yph："+yph);
			//19.是否车载-52
			if("0".equals(sfcz)){
				sfcz="X";
			}else{
				sfcz="";
			}
			tables.setValue("ZCHEZ", sfcz);
			rs.writeLog("sfcz："+sfcz);
			//20.图纸号-33
			tables.setValue("ZTZH", tzbh);
			rs.writeLog("tzbh："+tzbh);
			//21.图纸版本-34
			tables.setValue("ZTZBB", bbh);
			rs.writeLog("bbh："+bbh);
			
			//第3个表名
			JCoTable tabless = tableParameterList.getTable("ZMARC_DATA");//获取表名
			tabless.appendRows(1);
			tabless.setRow(0);
			//1.集团
			tabless.setValue("MANDT", "800");
			//2.物料号码，无搜索帮助-22
			tabless.setValue("MATNR", sapwlbm);
			rs.writeLog("sapwlbm："+sapwlbm);
			//3.工厂-20
			tabless.setValue("WERKS", gc);
			rs.writeLog("gc："+gc);
			//4.报关编码-96
			tabless.setValue("ZBGBM", bgbm);
			rs.writeLog("bgbm："+bgbm);
			//5.报关名称-97
			tabless.setValue("ZBGMC", bgmc);
			rs.writeLog("bgmc："+bgmc);
			//6.海关计量单位-98
			tabless.setValue("ZMEINH", hgjldw);
			rs.writeLog("hgjldw："+hgjldw);
			//7.转换为基本计量单位的分母-99
			tabless.setValue("ZUMREN", zhwjbjldwdfm);//报关
			rs.writeLog("zhwjbjldwdfm："+zhwjbjldwdfm);
			//8.基本计量单位转换分子-100
			tabless.setValue("ZUMREZ", jbjldwzhfz);//报关
			rs.writeLog("jbjldwzhfz："+jbjldwzhfz);
			//9.来源-101，下拉
			if(!"".equals(lyy) && !"".equals(ly)){
				msg.append("两个来源仅能填写一个来源<br/>");
			}
			if(!"".equals(ly)){
				if("0".equals(ly)){
					//ly="进口";
					ly="1";
				}else if("1".equals(ly)){
					//ly="转厂";
					ly="2";
				}else if("2".equals(ly)){
					//ly="一般贸易";
					ly="3";
				}else if("3".equals(ly)){
					//ly="国内";
					ly="4";
				}
			}
			if(!"".equals(lyy)){
				if("0".equals(lyy)){
					lyy="1";
				}else if("1".equals(lyy)){
					lyy="2";
				}else if("2".equals(lyy)){
					lyy="3";
				}else if("3".equals(lyy)){
					lyy="4";
				}
				ly=lyy;
			}
			tabless.setValue("ZLAIY", ly);
			rs.writeLog("ly："+ly);
			//10.是否外销-102
			if("0".equals(sfwx)){
				sfwx="X";
			}else if("1".equals(sfwx)){
				sfwx="";
			}
			tabless.setValue("ZWAIX", sfwx);
			rs.writeLog("sfwx："+sfwx);
			//11.特控标识-103
			if("0".equals(tkbs)){
				tkbs="X";
			}else if("1".equals(tkbs)){
				tkbs="";
			}
			tabless.setValue("ZTKBZ", tkbs);
			rs.writeLog("tkbs："+tkbs);
			//12.税目分类-104
			tabless.setValue("ZSMFL", smfl);
			rs.writeLog("smfl："+smfl);
			//13.存放地点-106
			tabless.setValue("ZCFDD", cfdd);
			rs.writeLog("cfdd："+cfdd);
			//14.仓管员编号-107
			tabless.setValue("ZCGBH", cgybh);
			rs.writeLog("cgybh："+cgybh);
			//15.无卤-91
			if("0".equals(wl)){
				wl="X";
			}else if("1".equals(wl)){
				wl="";
			}
			tabless.setValue("ZWUL", wl);
			rs.writeLog("wl："+wl);
			//16.ROHS2.0-92
			if("0".equals(rohs20)){
				rohs20="X";
			}else if("1".equals(rohs20)){
				rohs20="";
			}
			tabless.setValue("ZROHS", rohs20);
			rs.writeLog("rohs20："+rohs20);
			//17.特别检查标识-93
			if("0".equals(tbjcbs)){
				tbjcbs="X";
			}else if("1".equals(tbjcbs)){
				tbjcbs="";
			}
			tabless.setValue("ZTBJC", tbjcbs);
			rs.writeLog("tbjcbs："+tbjcbs);
			//18.环境等级-94，下拉
			//A1索尼使用、A2非索尼使用
			if("0".equals(hjdj)){
				hjdj="A1";
			}else if("1".equals(hjdj)){
				hjdj="A2";
			}else{
				hjdj="";
			}
			tabless.setValue("ZHJDJ", hjdj);
			rs.writeLog("hjdj："+hjdj);
			//19.不含邻苯二甲酸酯(6P)-95
			if("0".equals(bhlbejsz6p)){
				bhlbejsz6p="X";
			}else if("1".equals(bhlbejsz6p)){
				bhlbejsz6p="";
			}
			tabless.setValue("ZBHLB", bhlbejsz6p);
			rs.writeLog("bhlbejsz6p："+bhlbejsz6p);
			//10.供应商，从上表挪动-32
			tabless.setValue("ZGYS", gys);
			rs.writeLog("gys："+gys);
			
			call.excute(function);
			//打印传输值
			rs.writeLog(function.toXML());
			
			//获取返回值
			JCoParameterList exportParameterList = function.getTableParameterList();
			JCoTable t1 = tableParameterList.getTable("IT_RETURN");
			//打印返回值
			wul=t1.getValue("MATNR").toString();
			gongc=t1.getValue("WERKS").toString();
			leix=t1.getValue("TYPE").toString();
			xinx=t1.getValue("MESSAGE").toString();
			rs.writeLog("MATNR的值："+wul+"</br>");//物料
			rs.writeLog("WERKS的值："+gongc+"</br>");//工厂
			rs.writeLog("TYPE的值："+leix+"</br>");//返回类型
			rs.writeLog("MESSAGE的值："+xinx+"</br>");//返回信息
		} catch (Exception e) {
			rs.writeLog("调用创建接口异常..."+e);
			msg.append("调用主数据接口异常，"+e);
		}
		
		RequestManager requestManager = requestInfo.getRequestManager();
		if("S".equals(leix)){//返回成功
			flag="1";
			//修改返回信息
			this.updateForm(requestInfo, xinx, wul);
		}else{//返回异常
			flag="0";
			requestManager.setMessageid("111000");
			msg.append(xinx);
			msg.insert(0, "很抱歉，提交失败。原因如下：<br/>");
			requestManager.setMessagecontent(msg.toString());
			//修改返回信息
			this.updateForm(requestInfo, wul+""+xinx, "");
		}
		
		
		return flag;
	}
	
	public String getCodeById(String id){
		RecordSet rs=new RecordSet();
		String sql="select scglybh as tt from uf_scgly where id="+id;
		boolean flag=rs.execute(sql);
		String code="";
		if(flag){
			while(rs.next()){
				code=rs.getString("tt");
			}
		}
		return code;
	}
	
	/**
	 * 查询此工厂、此物料类型此字段是否存在，存在则false
	 * @param gongc 工厂
	 * @param wullx 物料类型
	 * @param zid 字段
	 * @return
	 */
	private boolean judgeIt(String gongc,String wullx,String zid){
		boolean flag=true;
		RecordSet rs=new RecordSet();
		String sql="select count(1) as tt from uf_wy0817 where gongc='"+gongc+"' and wullx='"+wullx+"' and zid like '%"+zid+"%'";
		int count=-1;
		flag=rs.execute(sql);
		if(flag){
			while(rs.next()){
				count=rs.getInt("tt")<0?0:rs.getInt("tt");
				if(count>0){//存在
					flag=false;
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
	private boolean updateForm(RequestInfo rq,String fanhxx,String wlbianhao) {
		int formid = rq.getRequestManager().getFormid();
		formid = Math.abs(formid);  
		String requestid = rq.getRequestid();
		RecordSet rs = new RecordSet();
		String sql = "update formtable_main_"+formid+" set sapxxfh='"+fanhxx+"',wlbianhao='"+wlbianhao+"' where requestid='"+requestid+"'";
		return rs.execute(sql);
	}
	
}
