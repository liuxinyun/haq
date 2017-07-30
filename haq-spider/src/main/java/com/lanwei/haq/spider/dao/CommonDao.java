package com.lanwei.haq.spider.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Dao统一继承类，尽量不要在sql里面写死参数，而是通过java来指定，方便统一维护
 *
 * @author liuxinyun
 * @created 2016/12/22 10:07
 */
public interface CommonDao<T> {

    /**
     * DAO需要实现新增，并且返回新增过后的id，实现方式：例
     * <insert id="insertTopBranch" parameterType="entity" useGeneratedKeys="true" keyColumn="id" keyProperty="id"></insert>
     * 在insert标签上面添加  useGeneratedKeys="true"， 指定主键例keyColumn，指定需要赋值的属性keyProperty
     *
     * @return
     */
    int add(T t);

    /**
     * 需要实现删除，删除只能逻辑删除，即把isDel字段标记为T
     *
     * @param t
     */
    void del(T t);

    /**
     * 需要实现更新，更新默认需要指定更新时间
     *
     * @param t
     */
    void update(T t);

    /**
     * 需要实现查询，查询需要注意
     * 1：因为int，byte等基本类型的默认值，为默认值得情况下不应该作为查询条件，特殊情况需要注明;
     * 2：除了通过id查询之外的查询都要默认指定isDel为F，特殊情况需要注明;
     * 3：其他的查询可以通过封装此方法实现; 例：可以在service层封装一个getById方法用于通过id查询该实体;
     * 4：默认排序，1、第一字段为sort.升序 2、第二字段为modified.降序，3、第三字段为created.降序,特殊需求自定
     * 5：分页，需要分页的查询需要继承BaseEntity, 里面会封装pageStart和pageSize,需要需要设置总页数
     * 6：时间格式化，时间格式化尽量通过java的get set方法实现
     * 7：通过时间查询的时候，如果只精确到日，则需要注意1、起始日期后面加上 00:00:00 ，2、结束时间加上 23:59:59，其他精确度以此类推
     * 8：自己封装的查询如果返回的是list,尽量别返回null,可以返回一个空的ArrayList
     *
     * @param t
     * @return
     */
    List<T> query(T t);

    /**
     * 需要实现查询，用于查询统计数量，注意事项参见query方法
     *
     * @param t
     * @return
     */
    int count(T t);

    /**
     * 批量删除
     *
     * @param idArr  需要删除的id数组
     * @param userId 删除的用户id
     */
    void delList(@Param("idArr") int[] idArr, @Param("userId") int userId);
}
