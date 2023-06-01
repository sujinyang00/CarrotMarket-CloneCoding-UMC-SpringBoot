package com.example.demo.src.item;

import com.example.demo.config.BaseException;

import com.example.demo.src.item.model.GetItemRes;
import com.example.demo.src.item.model.Item;
import com.example.demo.src.item.model.PostItemReq;
import com.example.demo.src.item.model.PostItemRes;
import com.example.demo.src.user.UserDao;

import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PostLoginReq;
import com.example.demo.src.user.model.PostLoginRes;

import com.example.demo.src.user.model.User;

import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
//GET 요청들은 여기서 처리
@Service
public class ItemProvider {

    private final UserDao userDao;
    private final ItemDao itemDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ItemProvider(UserDao userDao, ItemDao itemDao, JwtService jwtService) {
        this.userDao = userDao;
        this.itemDao = itemDao;
        this.jwtService = jwtService;
    }

    public List<GetItemRes> getItems() throws BaseException{
        try{
            List<GetItemRes> getItemRes = itemDao.getItems();
            return getItemRes;
        }
        catch (Exception exception) {
            // Logger를 이용하여 에러를 로그에 기록한다
            logger.error("Error!", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetItemRes> getItemsByCategory(int itemId) throws BaseException{
        try{
            List<GetItemRes> getItemRes = itemDao.getItemsByCategory(itemId);
            return getItemRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetItemRes getItem(int itemId) throws BaseException {
        try {
            GetItemRes getItemRes = itemDao.getItems(itemId);
            return getItemRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }





}
