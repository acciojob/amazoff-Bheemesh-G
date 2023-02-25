package com.driver;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class OrderRepository {

  HashMap<String,Order> r = new HashMap<>();
  HashMap<String,Integer> p = new HashMap<>();
  HashMap<String, ArrayList<String>> st = new HashMap<>();

    public void addOrder(Order order)
    {

        r.put(order.getId(),order);
    }

    public void addPatner(String id)
    {
        p.put(id,0);
    }

    public void addOrderPartnerPair(String orderId,String partnerId)
    {
        if(r.containsKey(orderId) && p.containsKey(partnerId))
        {
            r.put(orderId,r.get(orderId));
            p.put(partnerId,p.get(partnerId));
            if(st.containsKey(partnerId))
            {
                ArrayList<String> temp = st.get(partnerId);
                temp.add(orderId);
                st.put(partnerId,temp);
            }
            else {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(orderId);
                st.put(partnerId,temp);
            }
        }
    }










}
