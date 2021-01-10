package com.glt.controller;

import com.glt.base.BaseController;
import com.glt.po.Item;
import com.glt.po.ItemCategory;
import com.glt.service.ItemCategoryService;
import com.glt.service.ItemService;
import com.glt.utils.Pager;
import com.glt.utils.SystemContext;
import com.glt.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemCategoryService itemCategoryService;

    /**
     * 分页查询商品列表
     */
    @RequestMapping("/findBySql")
    public String findBySql(Model model, Item item){
        String sql = "select * from item where isDelete = 0 ";
        if(!isEmpty(item.getName())){
            sql += " and name like '%" + item.getName() + "%' ";
        }
        sql += " order by id desc";//按照id降序
        Pager<Item> pagers = itemService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",item);
        return "item/item";
    }

    /**
     * 添加商品入口
     */
    @RequestMapping("/add")
    public String add(Model model){
        //返回商品类别，下拉栏
        String sql = "select * from item_category where isDelete = 0 and pid is not null order by id";
        List<ItemCategory> listBySqlReturnEntity = itemCategoryService.listBySqlReturnEntity(sql);
        model.addAttribute("types",listBySqlReturnEntity);
        return "item/add";
    }

    /**
     * 执行添加商品
     */
    @RequestMapping("/exAdd")
    public String exAdd(Item item, @RequestParam("file")CommonsMultipartFile[] files, HttpServletRequest request) throws IOException {
        //文件上传
        itemCommon(item, files, request);
        item.setGmNum(0);
        item.setIsDelete(0);
        item.setScNum(0);
        itemService.insert(item);
        return "redirect:/item/findBySql.action";
    }

    /**
     * 修改商品入口
     */
    @RequestMapping("/update")
    public String update(Integer id,Model model){
        Item obj = itemService.load(id);
        String sql = "select * from item_category where isDelete = 0 and pid is not null order by id";
        List<ItemCategory> listBySqlReturnEntity = itemCategoryService.listBySqlReturnEntity(sql);
        model.addAttribute("types",listBySqlReturnEntity);
        model.addAttribute("obj",obj);
        return "item/update";
    }

    /**
     * 执行修改商品
     */
    @RequestMapping("/exUpdate")
    public String exUpdate(Item item, @RequestParam("file")CommonsMultipartFile[] files, HttpServletRequest request) throws IOException {
        itemCommon(item, files, request);
        itemService.updateById(item);
        return "redirect:/item/findBySql.action";
    }

    /**
     * 新增和更新的公共方法
     */
    private void itemCommon(Item item, @RequestParam("file") CommonsMultipartFile[] files, HttpServletRequest request) throws IOException {
        if(files.length>0) {

                //创建随机数
                String n = UUIDUtils.create();
                String path = SystemContext.getRealPath() + "\\resource\\ueditor\\upload\\" + n + files[0].getOriginalFilename();
                File newFile = new File(path);
                //通过CommonsMultipartFile的方法直接写文件
                files[0].transferTo(newFile);
                item.setUrl1(request.getContextPath()+"\\resource\\ueditor\\upload\\" + n + files[0].getOriginalFilename());


        }
        ItemCategory byId = itemCategoryService.getById(item.getCategoryIdTwo());
        item.setCategoryIdOne(byId.getPid());
    }

    /**
     * 商品下架
     */
    @RequestMapping("/delete")
    public String delete(Integer id){
        Item obj = itemService.load(id);
        obj.setIsDelete(1);
        itemService.updateById(obj);
        return "redirect:/item/findBySql.action";
    }

    /**
     * 按关键字或者二级分类查询
     */
    @RequestMapping("/shoplist")
    public String shoplist(Item item,String condition,Model model){
        String sql = "select * from item where isDelete=0";
        if(!isEmpty(item.getCategoryIdTwo())){//根据二级分类筛选商品
            sql +=" and category_id_two = " +item.getCategoryIdTwo();
        }
        if(!isEmpty(condition)){//关键词搜索
            sql += " and name like '%" + condition +"%' ";
            model.addAttribute("condition",condition);
        }
        if(!isEmpty(item.getPrice())){//通过价格降序
            sql += " order by (price+0) desc";
        }
        if(!isEmpty(item.getGmNum())){//销量降序
            sql += " order by gmNum desc";
        }
        if(isEmpty(item.getPrice())&&isEmpty(item.getGmNum())){
            sql += " order by id desc";
        }
        Pager<Item> pagers = itemService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",item);
        return "item/shoplist";
    }

    @RequestMapping("/view")
    public String view(Integer id,Model model){
        Item obj = itemService.load(id);
        model.addAttribute("obj",obj);
        return "item/view";
    }
}
