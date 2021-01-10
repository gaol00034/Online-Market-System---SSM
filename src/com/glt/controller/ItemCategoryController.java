package com.glt.controller;

import com.glt.base.BaseController;
import com.glt.po.ItemCategory;
import com.glt.po.Item;
import com.glt.service.ItemCategoryService;
import com.glt.service.ItemService;
import com.glt.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
/**
 * 类目c层
 */
@Controller
@RequestMapping("/itemCategory")
public class ItemCategoryController extends BaseController {

    @Autowired
    private ItemCategoryService itemCategoryService;
    @Autowired
    private ItemService itemService;

    /**
     * 分页查询类目列表
     */
    @RequestMapping("/findBySql")
    public String findBySql(Model model,ItemCategory itemCategory){
        String sql = "select * from item_category where isDelete = 0 and pid is null";
        if(!isEmpty(itemCategory.getName())){
            sql += " and name like '%" + itemCategory.getName() + "%'";
        }
        sql += " order by id";//按照升序
        Pager<ItemCategory> pagers = itemCategoryService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",itemCategory);
        return "itemCategory/itemCategory";
    }

    /**
     * 转向到新增一级类目页面
     */
    @RequestMapping(value = "/add")
    public String add(){
        return "itemCategory/add";
    }

    /**
     * 新增一级类目保存功能
     */
    @RequestMapping("/exAdd")
    public String exAdd(ItemCategory itemCategory){
        itemCategory.setIsDelete(0);
        itemCategoryService.insert(itemCategory);
        return "redirect:/itemCategory/findBySql.action";
    }

    /**
     * 转向到修改一级类目页面
     * 点击修改按钮，传入当前对象的id
     */
    @RequestMapping(value = "/update")
    public String update(Integer id,Model model){
        //通过主键返回一个实体
        ItemCategory obj = itemCategoryService.load(id);
        model.addAttribute("obj",obj);
        return "itemCategory/update";
    }

    /**
     * 修改一级类目，更新数据库
     */
    @RequestMapping("/exUpdate")
    public String exUpdate(ItemCategory itemCategory){
        //通过id更新
       itemCategoryService.updateById(itemCategory);
        return "redirect:/itemCategory/findBySql.action";
    }

    /**
     * 删除类目
     */
    @RequestMapping("/delete")
    public String delete(Integer id){
        ItemCategory load = itemCategoryService.load(id);
        String s = "select * from item where category_id_one=" + id + " order by id";
        List<Item> l = itemService.listBySqlReturnEntity(s);
        System.out.println(l);
        if(l.isEmpty()) {//此类别下无商品
            // 删除本身
            load.setIsDelete(1);
            itemCategoryService.updateById(load);
            //将下级也删除
            String sql = "update item_category set isDelete=1 where pid=" + id;
            itemCategoryService.updateBysql(sql);
            return "redirect:/itemCategory/findBySql.action";
        }else
            return "redirect:/itemCategory/findBySql.action";
    }

    /**
     * 查看二级类目
     */
    @RequestMapping("/findBySql2")
    public String findBySql2(ItemCategory itemCategory,Model model){
        String sql = "select * from item_category where isDelete=0 and pid="+itemCategory.getPid()+" order by id";
        Pager<ItemCategory> pagers = itemCategoryService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",itemCategory);
        return "itemCategory/itemCategory2";
    }

    /**
     * 转向到新增二级类目页面
     */
    @RequestMapping(value = "/add2")
    public String add2(int pid,Model model){
        model.addAttribute("pid",pid);
        return "itemCategory/add2";
    }

    /**
     * 新增二级类目保存功能
     */
    @RequestMapping("/exAdd2")
    public String exAdd2(ItemCategory itemCategory){
        itemCategory.setIsDelete(0);
        itemCategoryService.insert(itemCategory);
        return "redirect:/itemCategory/findBySql2.action?pid="+itemCategory.getPid();
    }

    /**
     * 转向到修改二级类目页面
     */
    @RequestMapping(value = "/update2")
    public String update2(Integer id,Model model){
        ItemCategory obj = itemCategoryService.load(id);
        model.addAttribute("obj",obj);
        return "itemCategory/update2";
    }

    /**
     * 修改二级类目
     */
    @RequestMapping("/exUpdate2")
    public String exUpdate2(ItemCategory itemCategory){
        itemCategoryService.updateById(itemCategory);
        return "redirect:/itemCategory/findBySql2.action?pid="+itemCategory.getPid();
    }

    /**
     * 删除二级类目
     */
    @RequestMapping("/delete2")
    public String delete2(Integer id,Integer pid){
        ItemCategory load = itemCategoryService.load(id);
        String s = "select * from item where category_id_two=" + id + " order by id";
        List<Item> l = itemService.listBySqlReturnEntity(s);
        System.out.println(l);
        if(l.isEmpty()){
            //删除本身
            String sql = "update item_category set isDelete=1 where id=" + id;
            itemCategoryService.updateBysql(sql);
        }
        return "redirect:/itemCategory/findBySql2.action?pid="+pid;
    }
}
