/**
  * Copyright 2018 bejson.com 
  */
package com.baidu.speech.test.Data;
import java.util.List;

/**
 * Auto-generated: 2018-11-21 10:38:31
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Course_Info {


	private List<CoursesBean> courses;

	public List<CoursesBean> getCourses() {
		return courses;
	}

	public void setCourses(List<CoursesBean> courses) {
		this.courses = courses;
	}

	public static class CoursesBean {
		/**
		 * id : 5bed5e6ecbd7a1c8067cbc67
		 * name : 创造性思维与创新方法
		 * introduction : 创新——方法——创造——思维——实践，《创造性思维与创新方法》给你带来更多的锻炼、全新的思维方式、更多创新的可能！
		 * thumbnail_id : 5bee79507a2f2e070a58d331
		 * chapters : [{"chapters":[{"name":"1-1 课程简介","video_id":"5bed5853cbd7a1c8067cbc60"},{"name":"1-2 困惑与思考","video_id":"5bed5a19cbd7a1c8067cbc61"},{"name":"1-3 什么是创造？","video_id":"5bed5b6ecbd7a1c8067cbc62"}],"name":"第一章 导论"},{"chapters":[{"name":"2-1 创造性思维概念","video_id":"5bed5cc3cbd7a1c8067cbc63"},{"name":"2-2 创造性思维的特征","video_id":"5bed5a19cbd7a1c8067cbc64"},{"name":"2-2 思维定势","video_id":"5bed5a19cbd7a1c8067cbc65"}],"name":"第二章 创造性思维及思维定势"}]
		 */

		private String id;
		private String name;
		private String introduction;
		private String thumbnail_id;
		private List<ChaptersBeanX> chapters;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIntroduction() {
			return introduction;
		}

		public void setIntroduction(String introduction) {
			this.introduction = introduction;
		}

		public String getThumbnail_id() {
			return thumbnail_id;
		}

		public void setThumbnail_id(String thumbnail_id) {
			this.thumbnail_id = thumbnail_id;
		}

		public List<ChaptersBeanX> getChapters() {
			return chapters;
		}

		public void setChapters(List<ChaptersBeanX> chapters) {
			this.chapters = chapters;
		}

		public static class ChaptersBeanX {
			/**
			 * chapters : [{"name":"1-1 课程简介","video_id":"5bed5853cbd7a1c8067cbc60"},{"name":"1-2 困惑与思考","video_id":"5bed5a19cbd7a1c8067cbc61"},{"name":"1-3 什么是创造？","video_id":"5bed5b6ecbd7a1c8067cbc62"}]
			 * name : 第一章 导论
			 */

			private String name;
			private List<ChaptersBean> chapters;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public List<ChaptersBean> getChapters() {
				return chapters;
			}

			public void setChapters(List<ChaptersBean> chapters) {
				this.chapters = chapters;
			}

			public static class ChaptersBean {
				/**
				 * name : 1-1 课程简介
				 * video_id : 5bed5853cbd7a1c8067cbc60
				 */

				private String name;
				private String video_id;
				private List<ChaptersBean> chapters;

				public List<ChaptersBean> getChapters() {
					return chapters;
				}

				public void setChapters(List<ChaptersBean> chapters) {
					this.chapters = chapters;
				}

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getVideo_id() {
					return video_id;
				}

				public void setVideo_id(String video_id) {
					this.video_id = video_id;
				}
			}
		}
	}
}
