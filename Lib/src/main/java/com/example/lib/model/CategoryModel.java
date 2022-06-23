package com.example.lib.model;

import java.io.Serializable;

public class CategoryModel implements Serializable
{
    private String name;

    private Integer id;

    private String describes;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Integer getId ()
    {
        return id;
    }

    public void setId (Integer id)
    {
        this.id = id;
    }

    public String getDescribes ()
    {
        return describes;
    }

    public void setDescribes (String describes)
    {
        this.describes = describes;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", id = "+id+", describes = "+describes+"]";
    }
}