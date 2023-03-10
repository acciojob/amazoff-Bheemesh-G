package com.driver;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

  HashMap<String,Order> r = new HashMap<>();
  HashMap<String,DeliveryPartner> p = new HashMap<>();
  HashMap<String, List<String>> st = new HashMap<>();
  HashMap<String,String> ass = new HashMap<>();

    public void addOrder(Order order)
    {


            r.put(order.getId(),order);
    }

    public void addPartner(String id)
    {
        DeliveryPartner deliveryPartner = new DeliveryPartner(id);
        p.put(id,deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId,String partnerId)
    {
        if(r.containsKey(orderId) && p.containsKey(partnerId))
        {
            ass.put(orderId,partnerId);
            r.put(orderId,r.get(orderId));
            p.put(partnerId,p.get(partnerId));
            if(st.containsKey(partnerId))
            {
                List<String> temp = st.get(partnerId);
                temp.add(orderId);
                st.put(partnerId,temp);
            }
            else {
                List<String> temp = new ArrayList<>();
                temp.add(orderId);
                st.put(partnerId,temp);
            }

            DeliveryPartner dp = p.get(partnerId);
           int deliverys = dp.getNumberOfOrders();
           dp.setNumberOfOrders(deliverys+1);

        }
    }

    public Order getOrderById(String orderId)
    {
        if(r.containsKey(orderId))
        {
            return r.get(orderId);
        }

        return null;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        if (p.containsKey(partnerId)) {
            return p.get(partnerId);
        }
        return null;
    }

    public int getOrderCountByPartnerId(String partnerId)
    {
        if(st.containsKey(partnerId))
        {
            return st.get(partnerId).size();
        }

        return 0;
    }

    public List<String> getOrdersByPartnerId(String partnerId)
    {
        if(st.containsKey(partnerId))
        {
            return st.get(partnerId);
        }

        return new ArrayList<>();
    }

    public List<String> getAllOrders()
    {
        List<String> temp = new ArrayList<>();
        for(String i:r.keySet())
        {
            temp.add(i);
        }

        return temp;
    }

    public int getCountOfUnassignedOrders()
    {
        int count = r.size()-ass.size();

        return count;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId)
    {
        String []temp = time.split(":");
        int hour = Integer.valueOf(temp[0]);
        int min = Integer.valueOf(temp[1]);
        int givenTime = (60*hour)+min;

        List<String> list = st.get(partnerId);
        int count =0;
        for(String i:list)
        {
            Order order = r.get(i);
            int delTime = order.getDeliveryTime();
            if(delTime>givenTime)
            {
                count++;
            }
        }

        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId)
    {
        String time = "";
        List<String> list = st.get(partnerId);
        int deliveryTime = 0;
        for (String s : list) {
            Order order = r.get(s);
            deliveryTime = Math.max(deliveryTime, order.getDeliveryTime());
        }
        int hour = deliveryTime / 60;
        String sHour = "";
        if (hour < 10) {
            sHour = "0" + String.valueOf(hour);
        } else {
            sHour = String.valueOf(hour);
        }
        int min = deliveryTime % 60;
        String sMin = "";
        if (min < 10) {
            sMin = "0" + String.valueOf(min);
        } else {
            sMin = String.valueOf(min);
        }
        time = sHour + ":" + sMin;
        return time;
    }

    public void deletePartnerById(String partnerId)
    {
       List<String> list = st.get(partnerId);
       for(String i:list)
       {
           ass.remove(i);
       }

       if(p.containsKey(partnerId))
       {
           p.remove(partnerId);
       }

       st.remove(partnerId);
    }

    public void deleteOrderById(String orderId)
    {

        //remove from orders
        r.remove(orderId);
        //remove from assigened hashmap
        String parent = ass.get(orderId);
        ass.remove(orderId);
        List<String> list = new ArrayList<>();
        for(String i:list)
        {
            if(orderId.equals(i))
            {
                list.remove(i);
            }
        }
        st.put(parent,list);

    }


















}
