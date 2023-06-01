package com.example.demo.src.item;


import com.example.demo.src.item.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ItemDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }









    //GET
    public Item getItem(PostItemReq postItemReq){
        String getPwdQuery = "select userIdx, password,email,userName,ID from UserInfo where ID = ?";
        int getPwdParams = postItemReq.getItemId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new Item(
                        rs.getInt("itemId"),
                        rs.getInt("itemCategory"),
                        rs.getString("postTitle"),
                        rs.getInt("postWriterId"),
                        rs.getString("postWrittenDate"),
                        rs.getString("postContent"),
                        rs.getString("postImgUrl"),
                        rs.getInt("itemPrice"),
                        rs.getInt("itemStatus"),
                        rs.getInt("itemLikedCNT"),
                        rs.getInt("locationCode"),
                        rs.getString("pullUpDate"),
                        rs.getBoolean("priceOffer")
                ),
                getPwdParams
        );

    }

    public GetItemRes getItems(int itemIdx){
        String getItemQuery = "select * from TradeProdyct where trade_itemId = ?";
        int getItemParams = itemIdx;
        return this.jdbcTemplate.queryForObject(getItemQuery,
                (rs, rowNum) -> new GetItemRes(
                        rs.getInt("itemIdx"),
                        rs.getInt("itemCategory"),
                        rs.getString("postTitle"),
                        rs.getInt("postWriterId"),
                        rs.getString("postWrittenDate"),
                        rs.getString("postContent"),
                        rs.getString("postImgUrl"),
                        rs.getInt("itemPrice"),
                        rs.getInt("itemStatus"),
                        rs.getInt("itemLikedCNT"),
                        rs.getInt("locationCode"),
                        rs.getString("pullUpDate"),
                        rs.getBoolean("priceOffer")),
                getItemParams);
    }

    public List<GetItemRes> getItems(){
        String getItemsQuery = "select * from TradeProduct";
        return this.jdbcTemplate.query(getItemsQuery,
                (rs,rowNum) -> new GetItemRes(
                        rs.getInt("itemIdx"),
                        rs.getInt("itemCategory"),
                        rs.getString("postTitle"),
                        rs.getInt("postWriterId"),
                        rs.getString("postWrittenDate"),
                        rs.getString("postContent"),
                        rs.getString("postImgUrl"),
                        rs.getInt("itemPrice"),
                        rs.getInt("itemStatus"),
                        rs.getInt("itemLikedCNT"),
                        rs.getInt("locationCode"),
                        rs.getString("pullUpDate"),
                        rs.getBoolean("priceOffer"))
        );
    }

    public GetItemRes getItem(int itemId){
        String getItemQuery = "select * from TradeProduct where trade_itemId = ?";
        int getItemParams = itemId;
        return this.jdbcTemplate.queryForObject(getItemQuery,
                (rs,rowNum) -> new GetItemRes(
                        rs.getInt("itemIdx"),
                        rs.getInt("itemCategory"),
                        rs.getString("postTitle"),
                        rs.getInt("postWriterId"),
                        rs.getString("postWrittenDate"),
                        rs.getString("postContent"),
                        rs.getString("postImgUrl"),
                        rs.getInt("itemPrice"),
                        rs.getInt("itemStatus"),
                        rs.getInt("itemLikedCNT"),
                        rs.getInt("locationCode"),
                        rs.getString("pullUpDate"),
                        rs.getBoolean("priceOffer")),
                getItemParams);
    }

    //GET
    public List<GetItemRes> getItemsByCategory(int itemId){
        String getItemsByCategoryQuery = "select trage_category from TradeProduct where trade_itemId =?";
        Integer getItemsByCategoryParams = itemId;
        return this.jdbcTemplate.query(getItemsByCategoryQuery,
                (rs, rowNum) -> new GetItemRes(
                        rs.getInt("itemIdx"),
                        rs.getInt("itemCategory"),
                        rs.getString("postTitle"),
                        rs.getInt("postWriterId"),
                        rs.getString("postWrittenDate"),
                        rs.getString("postContent"),
                        rs.getString("postImgUrl"),
                        rs.getInt("itemPrice"),
                        rs.getInt("itemStatus"),
                        rs.getInt("itemLikedCNT"),
                        rs.getInt("locationCode"),
                        rs.getString("pullUpDate"),
                        rs.getBoolean("priceOffer")
                ), getItemsByCategoryParams);
    }

    //GET
    public GetItemRes getItemByItemID(int itemIdx){
        String getItemQuery = "select * from TradeProduct where trade_itemId = ?";
        int getItemParams = itemIdx;
        return this.jdbcTemplate.queryForObject(getItemQuery,
                (rs, rowNum) -> new GetItemRes(
                        rs.getInt("itemIdx"),
                        rs.getInt("itemCategory"),
                        rs.getString("postTitle"),
                        rs.getInt("postWriterId"),
                        rs.getString("postWrittenDate"),
                        rs.getString("postContent"),
                        rs.getString("postImgUrl"),
                        rs.getInt("itemPrice"),
                        rs.getInt("itemStatus"),
                        rs.getInt("itemLikedCNT"),
                        rs.getInt("locationCode"),
                        rs.getString("pullUpDate"),
                        rs.getBoolean("priceOffer")
                        ), getItemParams);
    }

    //POST
    public int createItem(PostItemReq postItemReq){
        String createItemQuery = "insert into TradeProduct (trage_category, trade_post_title, trade_post_writer, trade_post_writtenDate, trade_post_content, trade_post_imgUrl, trade_itemPrice, trade_location, trade_price_offer) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] createItemParams = new Object[]{postItemReq.getItemCategory(), postItemReq.getPostTitle(), postItemReq.getItemId(), postItemReq.getPostWrittenDate(), postItemReq.getPostContent(), postItemReq.getItemPrice(), postItemReq.getLocationCode(), postItemReq.getPriceOffer()};
        this.jdbcTemplate.update(createItemQuery, createItemParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    //PUT
    public int modifyItemPost(PutItemPostReq putItemPostReq){
        String modifyUserNameQuery = "update TradeProduct " +
                "set " +
                    "trade_post_updatedDate = CURRENT_TIMESTAMP, " +
                    "  trade_category = ?," +
                    "  trade_post_title = ?," +
                    "  trade_post_content = ?," +
                    "  trade_post_imgUrl = ?," +
                    "  trade_itemPrice = ?," +
                    "  trade_itemStatus = ?," +
                    "  trade_location = ?," +
                    "  trade_price_offer = ? " +
                    "where trade_itemId = ? ";
        Object[] modifyUserNameParams = new Object[]{putItemPostReq.getUpdatedDate(), putItemPostReq.getItemCategory(), putItemPostReq.getPostTitle(), putItemPostReq.getPostContent(), putItemPostReq.getPostImgUrl(), putItemPostReq.getItemPrice(), putItemPostReq.getItemStatus(), putItemPostReq.getLocationCode(), putItemPostReq.getPriceOffer(), putItemPostReq.getItemId() };

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }


    //DELETE
    public int deleteItem(DeleteItemReq deleteItemReq){
        String deleteItemQuery = "delete from TradeProduct where trade_itemId = ? and trade_category = ?";
        Object[] deleteItemParams = new Object[]{deleteItemReq.getItemId(), deleteItemReq.getItemCategory()};
        this.jdbcTemplate.update(deleteItemQuery, deleteItemParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }


    //PATCH
    public int modifyUserNickName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update TradeProduct set user_nickName = ? where trade_itemId = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserId()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }








}

/*
- Route: Request에서 보낸 라우팅 처리
- Controller: Request를 처리하고 Response 해주는 곳. (Provider/Service에 넘겨주고 다시 받아온 결과값을 형식화), 형식적 Validation
- Provider/Service: 비즈니스 로직 처리, 의미적 Validation
- DAO: Data Access Object의 줄임말. Query가 작성되어 있는 곳.
*/