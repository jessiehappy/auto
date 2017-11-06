package auto.datamodel.controller.series;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SeriesListResult {
	
	/**
	 * 车系主键
	 */
	private Long id;
	/**
	 * 车系图片
	 */
	private String seriesImg;
	/**
	 * 名称 品牌 车系
	 */
	private String name;
	/**
	 * 代理此品牌下的此车系的经销商数量
	 */
	private Integer dealerNum;
	
	/**
	 * 此代金券发布时间  用于列表的排序
	 */
	private Long releaseTime;
}
