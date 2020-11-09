/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DTO;

import org.springframework.stereotype.Component;

/**
 *
 * @author recyn
 */
@Component
public class DTO {
    
    private Type1 var1;
    private Type2 var2;
    private Type3 var3;
    private Type4 var4;
    private Type5 var5;
    private Type6 var6;
    private Type7 var7;
    
    public DTO (Type1 var1, Type2 var2, Type3 var3, Type4 var4, Type5 var5, Type6 var6, Type7 var7){
        this.var1=var1;
        this.var2=var2;
        this.var3=var3;
        this.var4=var4;
        this.var5=var5;
        this.var6=var6;
        this.var7=var7;
    }
    public void setVar1(Type1 var1){
        this.var1= var1;
    }
    
    public Type1 getVar1(){
        return var1;
    }
    
    public void setVar2(Type2 var2){
        this.var2= var2;
    }
    
    public Type2 getVar2(){
        return var2;
    }
    
    public void setVar3(Type3 var3){
        this.var3= var3;
    }
    
    public Type3 getVar3(){
        return var3;
    }
    
    public void setVar4(Type4 var4){
        this.var4= var4;
    }
    
    public Type4 getVar4(){
        return var4;
    }
    
    public void setVar5(Type5 var5){
        this.var5= var5;
    }
    
    public Type5 getVar5(){
        return var5;
    }
    
    public void setVar6(Type6 var6){
        this.var6= var6;
    }
    
    public Type6 getVar6(){
        return var6;
    }
    
    public void setVar7(Type7 var7){
        this.var1= var1;
    }
    
    public Type7 getVar7(){
        return var7;
    }
}
