create view wv_deliveryorder as
select t2.requestid,
t1.id,
t1.wpbh,
t1.wlms,
t1.dw,
t1.xqsl,
(select workcode from hrmresource where id=t1.cgy) as cgygh,
t1.sy,
t1.sjlysl,
t1.lyrq
 from
 formtable_main_24_dt1 t1,
 formtable_main_24 t2
 where t1.mainid=t2.id
--requestid-流程请求ID--
--id-明细ID--
--wpbh-物料编号--
--wlms-物料描述--
--dw-单位--
--xqsl-需求数量--
--cgygh-仓管员工号--
--sy-事由--
--sjlysl-实际领用数量--
--lyrq-领用日期--