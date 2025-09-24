package io.github.terraria.logic.equipment;

import com.google.common.util.concurrent.FutureCallback;
import io.github.terraria.loading.RecordLoader;

import java.util.List;
import java.util.function.Function;

public class GenericItemFactory<T extends Item, S extends ItemType> implements ItemFactory{
    private final List<S> itemTypes;
    private final Function<S, T> itemFromType;
    public GenericItemFactory(List<S> itemTypes, Function<S, T> itemFromType){
        this.itemTypes = itemTypes;
        this.itemFromType = itemFromType;
    }

    public GenericItemFactory(String jsonName, Function<S, T> itemFromType, Class<S> clazz){
        this(RecordLoader.loadList(jsonName, clazz), itemFromType);
    }

    @Override
    public boolean contains(String name) {
        for(var type : itemTypes){
            if(type.name().equals(name))return true;
        }
        return false;
    }

    @Override
    public boolean contains(int id) {
        for(var type : itemTypes){
            if(type.id() == id)return true;
        }
        return false;
    }

    @Override
    public Item create(String name) {
        for(var type : itemTypes){
            if(type.name().equals(name)){
                return itemFromType.apply(type);
            }
        }
        return null;
    }

    @Override
    public Item create(int id) {
        for(var type : itemTypes){
            if(type.id() == id){
                return itemFromType.apply(type);
            }
        }
        return null;
    }
}
