package com.example.demo.src.item;


import com.example.demo.config.BaseException;
import com.example.demo.src.item.model.*;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
//POST PUT PATCH DELETE 요청 여기서 처리
@Service
public class ItemService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final ItemDao itemDao;
    private final UserProvider userProvider;
    private final ItemProvider itemProvider;
    private final JwtService jwtService;


    @Autowired
    public ItemService(UserDao userDao, ItemDao itemDao, UserProvider userProvider, ItemProvider itemProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.itemDao = itemDao;
        this.userProvider = userProvider;
        this.itemProvider = itemProvider;
        this.jwtService = jwtService;

    }

    //POST
    public PostItemRes createItem(PostItemReq postItemReq) throws BaseException {
        try{
            int itemIdx = itemDao.createItem(postItemReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(itemIdx);
            return new PostItemRes(itemIdx,jwt);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //DELETE
    public DeleteItemRes deleteItem(DeleteItemReq deleteItemReq) throws BaseException {
        try{
            int itemIdx = itemDao.deleteItem(deleteItemReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(itemIdx);
            return new DeleteItemRes(itemIdx,jwt);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //PATCH
    public void modifyUserNick(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = itemDao.modifyUserNickName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }



    //PUT
    public void modifyItemPost(PutItemPostReq putItemPostReq) throws BaseException {
        try{
            int result = itemDao.modifyItemPost(putItemPostReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }





}
