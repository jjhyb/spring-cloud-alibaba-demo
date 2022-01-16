package com.yibo.cmdb;

import com.alibaba.nacos.api.cmdb.pojo.*;
import com.alibaba.nacos.api.cmdb.spi.CmdbService;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: huangyibo
 * @Date: 2021/7/31 23:59
 * @Description:
 */
public class CmdbServiceImpl implements CmdbService {

    private int index = 1;

    private Map<String,Map<String, Entity>> entityMap = new ConcurrentHashMap<String, Map<String, Entity>>();

    private Map<String,Label> labelMap = new ConcurrentHashMap<String, Label>();

    /**
     * CMDB_DATA_TABLE:模拟cmdb数据库数据
     */
    public CmdbServiceImpl() {
        Label label = new Label();
        label.setName("cluster");
        Set<String> values = new HashSet<String>();
        values.add("shenzhen");
        values.add("shanghai");
        label.setValues(values);
        labelMap.put(label.getName(),label);
        entityMap.put(PreservedEntityTypes.ip.name(),new HashMap<String, Entity>());

        //深圳cluster的ip
        Entity entityShen = new Entity();
        entityShen.setName("192.168.50.253");
        entityShen.setType(PreservedEntityTypes.ip.name());
        Map<String,String> lableShen = new HashMap<String, String>();
        lableShen.put("cluster","shenzhen");
        entityShen.setLabels(lableShen);
        entityMap.get(PreservedEntityTypes.ip.name()).put(entityShen.getName(),entityShen);

        //上海cluster的ip
        Entity entityShang = new Entity();
        entityShang.setName("192.168.50.175");
        entityShang.setType(PreservedEntityTypes.ip.name());
        Map<String,String> lableShang = new HashMap<String, String>();
        lableShang.put("cluster","shanghai");
        entityShang.setLabels(lableShang);
        entityMap.get(PreservedEntityTypes.ip.name()).put(entityShang.getName(),entityShang);

    }

    public Set<String> getLabelNames() {
        Set<String> labelNames = new HashSet<>();
        labelMap.forEach((key,value) -> {
            labelNames.add(key);
        });
        return labelNames;
    }

    public Set<String> getEntityTypes() {
        Set<String> types = new HashSet<>();
        entityMap.forEach((key,value) -> {
            types.add(key);
        });
        return types;
    }

    public Label getLabel(String labelName) {
        return labelMap.get(labelName);
    }

    public String getLabelValue(String entityName, String entityType, String labelName) {
        return entityMap.get(entityType).get(entityName).getLabels().get(labelName);
    }

    public Map<String, String> getLabelValues(String entityName, String entityType) {
        return entityMap.get(entityType).get(entityName).getLabels();
    }

    public Map<String, Map<String, Entity>> getAllEntities() {
        return entityMap;
    }

    public List<EntityEvent> getEntityEvents(long timestamp) {
        Entity entity = new Entity();
        entity.setName("1.1.1.1");
        entity.setType(PreservedEntityTypes.ip.name());
        Map<String, String> labels = new HashMap<>();
        labels.put("cluster1", "x1" + ((index % 3) + 1));
        labels.put("cluster2", "x2" + ((index++ % 3) + 1));

        entity.setLabels(labels);

        entityMap.get(PreservedEntityTypes.ip.name()).put("1.1.1.1", entity);

        EntityEvent entityEvent = new EntityEvent();
        entityEvent.setEntityName("1.1.1.1");
        entityEvent.setEntityType("PreservedEntityTypes.ip.name()");
        entityEvent.setType(EntityEventType.ENTITY_ADD_OR_UPDATE);
        List<EntityEvent> list = new ArrayList<>();
        list.add(entityEvent);

        return list;
    }

    public Entity getEntity(String entityName, String entityType) {
        return entityMap.get(entityType).get(entityName);
    }
}
