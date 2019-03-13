package com.group33;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class Infomation extends AppCompatActivity {
    private Heros hero;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        //获取传来的英雄信息
        hero = (Heros)getIntent().getSerializableExtra("Hero");
        //设置称号
        TextView heroTitle = findViewById(R.id.heroTitle);
        heroTitle.setText(hero.getHeroTitle());
        //设置名字
        TextView heroName = findViewById(R.id.heroName);
        heroName.setText(hero.getHeroName());
        //设置职业
        TextView heroJob = findViewById(R.id.heroJob);
        heroJob.setText(hero.getHeroJob());
        //设置头像
        ImageView heroIcon = findViewById(R.id.heroIcon);
        String name = hero.getHeroName();
        switch(name)
        {
            case "阿珂":heroIcon.setImageResource(R.mipmap.ake);break;
            case "安琪拉":heroIcon.setImageResource(R.mipmap.anqila);break;
            case "白起":heroIcon.setImageResource(R.mipmap.baiqi);break;
            case "百里守约":heroIcon.setImageResource(R.mipmap.bailishouyue);break;
            case "百里玄策":heroIcon.setImageResource(R.mipmap.bailixuance);break;
            case "扁鹊":heroIcon.setImageResource(R.mipmap.bianque);break;
            case "不知火舞":heroIcon.setImageResource(R.mipmap.buzhihuowu);break;
            case "蔡文姬":heroIcon.setImageResource(R.mipmap.caiwenji);break;
            case "曹操":heroIcon.setImageResource(R.mipmap.caocao);break;
            case "成吉思汗":heroIcon.setImageResource(R.mipmap.chengjisihan);break;
            case "程咬金":heroIcon.setImageResource(R.mipmap.chengyaojin);break;
            case "达摩":heroIcon.setImageResource(R.mipmap.damo);break;
            case "妲己":heroIcon.setImageResource(R.mipmap.daji);break;
            case "大乔":heroIcon.setImageResource(R.mipmap.daqiao);break;
            case "狄仁杰":heroIcon.setImageResource(R.mipmap.direnjie);break;
            case "典韦":heroIcon.setImageResource(R.mipmap.dianwei);break;
            case "貂蝉":heroIcon.setImageResource(R.mipmap.diaochan);break;
            case "东皇太一":heroIcon.setImageResource(R.mipmap.donghuangtaiyi);break;
            case "盾山":heroIcon.setImageResource(R.mipmap.dunshan);break;
            case "伽罗":heroIcon.setImageResource(R.mipmap.jialuo);break;
            case "干将莫邪":heroIcon.setImageResource(R.mipmap.ganjiangmoye);break;
            case "高渐离":heroIcon.setImageResource(R.mipmap.gaojianli);break;
            case "公孙离":heroIcon.setImageResource(R.mipmap.gongsunli);break;
            case "宫本武藏":heroIcon.setImageResource(R.mipmap.gongbenwuzang);break;
            case "关羽":heroIcon.setImageResource(R.mipmap.guanyu);break;
            case "鬼谷子":heroIcon.setImageResource(R.mipmap.guiguzi);break;
            case "韩信":heroIcon.setImageResource(R.mipmap.hanxin);break;
            case "后羿":heroIcon.setImageResource(R.mipmap.houyi);break;
            case "花木兰":heroIcon.setImageResource(R.mipmap.huamulan);break;
            case "黄忠":heroIcon.setImageResource(R.mipmap.huangzhong);break;
            case "姜子牙":heroIcon.setImageResource(R.mipmap.jiangziya);break;
            case "橘右京":heroIcon.setImageResource(R.mipmap.juyoujing);break;
            case "铠":heroIcon.setImageResource(R.mipmap.kai);break;
            case "狂铁":heroIcon.setImageResource(R.mipmap.kuangtie);break;
            case "兰陵王":heroIcon.setImageResource(R.mipmap.lanlingwang);break;
            case "老夫子":heroIcon.setImageResource(R.mipmap.laofuzi);break;
            case "李白":heroIcon.setImageResource(R.mipmap.libai);break;
            case "李元芳":heroIcon.setImageResource(R.mipmap.liyuanfang);break;
            case "廉颇":heroIcon.setImageResource(R.mipmap.lianpo);break;
            case "刘邦":heroIcon.setImageResource(R.mipmap.liubang);break;
            case "刘备":heroIcon.setImageResource(R.mipmap.liubei);break;
            case "刘禅":heroIcon.setImageResource(R.mipmap.liushan);break;
            case "鲁班七号":heroIcon.setImageResource(R.mipmap.lubanqihao);break;
            case "露娜":heroIcon.setImageResource(R.mipmap.luna);break;
            case "吕布":heroIcon.setImageResource(R.mipmap.lvbu);break;
            case "马可波罗":heroIcon.setImageResource(R.mipmap.makeboluo);break;
            case "梦奇":heroIcon.setImageResource(R.mipmap.mengqi);break;
            case "米莱狄":heroIcon.setImageResource(R.mipmap.milaidi);break;
            case "芈月":heroIcon.setImageResource(R.mipmap.miyue);break;
            case "明世隐":heroIcon.setImageResource(R.mipmap.mingshiyin);break;
            case "墨子":heroIcon.setImageResource(R.mipmap.mozi);break;
            case "哪吒":heroIcon.setImageResource(R.mipmap.nezha);break;
            case "娜可露露":heroIcon.setImageResource(R.mipmap.nakelulu);break;
            case "牛魔":heroIcon.setImageResource(R.mipmap.niumo);break;
            case "女娲":heroIcon.setImageResource(R.mipmap.nvwa);break;
            case "裴擒虎":heroIcon.setImageResource(R.mipmap.peiqinhu);break;
            case "沈梦溪":heroIcon.setImageResource(R.mipmap.shenmengxi);break;
            case "司马懿":heroIcon.setImageResource(R.mipmap.simayi);break;
            case "苏烈":heroIcon.setImageResource(R.mipmap.sulie);break;
            case "孙膑":heroIcon.setImageResource(R.mipmap.sunbin);break;
            case "孙策":heroIcon.setImageResource(R.mipmap.sunce);break;
            case "孙尚香":heroIcon.setImageResource(R.mipmap.sunshangxiang);break;
            case "孙悟空":heroIcon.setImageResource(R.mipmap.sunwukong);break;
            case "太乙真人":heroIcon.setImageResource(R.mipmap.taiyizhenren);break;
            case "王昭君":heroIcon.setImageResource(R.mipmap.wangzhaojun);break;
            case "武则天":heroIcon.setImageResource(R.mipmap.wuzetian);break;
            case "夏侯惇":heroIcon.setImageResource(R.mipmap.xiahoudun);break;
            case "项羽":heroIcon.setImageResource(R.mipmap.xiangyu);break;
            case "小乔":heroIcon.setImageResource(R.mipmap.xiaoqiao);break;
            case "雅典娜":heroIcon.setImageResource(R.mipmap.yadianna);break;
            case "亚瑟":heroIcon.setImageResource(R.mipmap.yase);break;
            case "杨戬":heroIcon.setImageResource(R.mipmap.yangjian);break;
            case "杨玉环":heroIcon.setImageResource(R.mipmap.yangyuhuan);break;
            case "奕星":heroIcon.setImageResource(R.mipmap.yixing);break;
            case "嬴政":heroIcon.setImageResource(R.mipmap.yingzheng);break;
            case "虞姬":heroIcon.setImageResource(R.mipmap.yuji);break;
            case "元歌":heroIcon.setImageResource(R.mipmap.yuange);break;
            case "张飞":heroIcon.setImageResource(R.mipmap.zhangfei);break;
            case "张良":heroIcon.setImageResource(R.mipmap.zhangliang);break;
            case "赵云":heroIcon.setImageResource(R.mipmap.zhaoyun);break;
            case "甄姬":heroIcon.setImageResource(R.mipmap.zhenji);break;
            case "钟馗":heroIcon.setImageResource(R.mipmap.zhongkui);break;
            case "钟无艳":heroIcon.setImageResource(R.mipmap.zhongwuyan);break;
            case "周瑜":heroIcon.setImageResource(R.mipmap.zhouyu);break;
            case "诸葛亮":heroIcon.setImageResource(R.mipmap.zhugeliang);break;
            case "庄周":heroIcon.setImageResource(R.mipmap.zhuangzhou);break;
        }
        //设置生存能力
        int heroLive = hero.getHeroLive();
        ImageView heroLiveImg = findViewById(R.id.heroLiveImg);
        switch(heroLive)
        {
            case 1 : heroLiveImg.setImageResource(R.mipmap.star1); break;
            case 2 : heroLiveImg.setImageResource(R.mipmap.star2); break;
            case 3 : heroLiveImg.setImageResource(R.mipmap.star3); break;
            case 4 : heroLiveImg.setImageResource(R.mipmap.star4); break;
            case 5 : heroLiveImg.setImageResource(R.mipmap.star5); break;
        }
        //设置攻击伤害
        int heroAttack = hero.getHeroAttack();
        ImageView heroAttackImg = findViewById(R.id.heroAttackImg);
        switch(heroAttack)
        {
            case 1 : heroAttackImg.setImageResource(R.mipmap.star1); break;
            case 2 : heroAttackImg.setImageResource(R.mipmap.star2); break;
            case 3 : heroAttackImg.setImageResource(R.mipmap.star3); break;
            case 4 : heroAttackImg.setImageResource(R.mipmap.star4); break;
            case 5 : heroAttackImg.setImageResource(R.mipmap.star5); break;
        }
        //设置技能效果
        int heroSkill = hero.getHeroSkill();
        ImageView heroSkillImg = findViewById(R.id.heroSkillImg);
        switch (heroSkill)
        {
            case 1 : heroSkillImg.setImageResource(R.mipmap.star1); break;
            case 2 : heroSkillImg.setImageResource(R.mipmap.star2); break;
            case 3 : heroSkillImg.setImageResource(R.mipmap.star3); break;
            case 4 : heroSkillImg.setImageResource(R.mipmap.star4); break;
            case 5 : heroSkillImg.setImageResource(R.mipmap.star5); break;
        }
        //设置上手难度
        int heroLevel = hero.getHeroLevel();
        ImageView heroLevelImg = findViewById(R.id.heroLevelImg);
        switch (heroLevel)
        {
            case 1 : heroLevelImg.setImageResource(R.mipmap.star1); break;
            case 2 : heroLevelImg.setImageResource(R.mipmap.star2); break;
            case 3 : heroLevelImg.setImageResource(R.mipmap.star3); break;
            case 4 : heroLevelImg.setImageResource(R.mipmap.star4); break;
            case 5 : heroLevelImg.setImageResource(R.mipmap.star5); break;
        }
        //设置人气指数
        int heroPop = hero.getHeroPop();
        ImageView heroPopImg = findViewById(R.id.heroPopImg);
        switch(heroPop)
        {
            case 1 : heroPopImg.setImageResource(R.mipmap.star1); break;
            case 2 : heroPopImg.setImageResource(R.mipmap.star2); break;
            case 3 : heroPopImg.setImageResource(R.mipmap.star3); break;
            case 4 : heroPopImg.setImageResource(R.mipmap.star4); break;
            case 5 : heroPopImg.setImageResource(R.mipmap.star5); break;
        }
        //设置建议分路
        TextView heroWay = findViewById(R.id.heroWay);
        heroWay.setText(hero.getAdviceWay());
    }
}
