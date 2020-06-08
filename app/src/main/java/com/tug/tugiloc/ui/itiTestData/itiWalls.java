package com.tug.tugiloc.ui.itiTestData;

import com.tug.tugiloc.type.Wall;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Polyline;

import java.lang.reflect.Array;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class itiWalls {
    static public ArrayList<Wall> getITIWalls(){
        ArrayList<Wall> walls = new ArrayList<Wall>();

        /*******************************************************************************************
         * ITI Outside Wall ---------------------------------------------------------------------- *
         ******************************************************************************************/
        walls.add(new Wall(
                new GeoPoint(47.05806439399922, 15.457633589296421),
                new GeoPoint(47.05846011980769, 15.458634412451573)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05846011980769, 15.458634412451573),
                new GeoPoint( 47.058506501021014, 15.458596560645589)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.058506501021014, 15.458596560645589),
                new GeoPoint( 47.058483609720504, 15.458538817043888)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.058483609720504, 15.458538817043888),
                new GeoPoint( 47.0585087812422, 15.458518663070834)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.0585087812422, 15.458518663070834),
                new GeoPoint( 47.0585087812422, 15.458518663070834)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.0585087812422, 15.458518663070834),
                new GeoPoint( 47.05857259204212, 15.458681830299724)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05857259204212, 15.458681830299724),
                new GeoPoint( 47.058620226679146, 15.458643529116785)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.058620226679146, 15.458643529116785),
                new GeoPoint( 47.05823181687283, 15.457663969890064)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05823181687283, 15.457663969890064),
                new GeoPoint( 47.05818637111995, 15.457704809178011)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05818637111995, 15.457704809178011),
                new GeoPoint( 47.05820675428937, 15.457758623524057)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05820675428937, 15.457758623524057),
                new GeoPoint( 47.0581827666147, 15.457778163693177)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.0581827666147, 15.457778163693177),
                new GeoPoint( 47.05811004372293, 15.457594803608828)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05811004372293, 15.457594803608828),
                new GeoPoint( 47.05806439399922, 15.457633589296421)
        ));

        /*******************************************************************************************
         * ITI Inside Wall ----------------------------------------------------------------------- *
         ******************************************************************************************/
        walls.add(new Wall(
                new GeoPoint(47.058484430907576, 15.458538391671112),
                new GeoPoint(47.05847089878569, 15.458504157361261)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05847089878569, 15.458504157361261),
                new GeoPoint( 47.0584942614155, 15.458485184486989)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.0584942614155, 15.458485184486989),
                new GeoPoint( 47.05850708557329, 15.458517637306329)
        ));

        walls.add(new Wall(
                new GeoPoint( 47.05846054236124, 15.458478177290885),
                new GeoPoint( 47.05833474805289, 15.458159379558822)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05833474805289, 15.458159379558822),
                new GeoPoint( 47.05835780733086, 15.458140436376084)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05835780733086, 15.458140436376084),
                new GeoPoint( 47.058483809361675, 15.4584590256502)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.058483809361675, 15.4584590256502),
                new GeoPoint( 47.05846054236124, 15.458478177290885)
        ));

        walls.add(new Wall(
                new GeoPoint( 47.058315707487694, 15.458111618276376),
                new GeoPoint( 47.0581987005245, 15.457815554275669)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.0581987005245, 15.457815554275669),
                new GeoPoint( 47.05822140913284, 15.45779566717465)
        ));

        //LAB North
        walls.add(new Wall(
                new GeoPoint( 47.05822140913284, 15.45779566717465),
                new GeoPoint( 47.05826871836141, 15.45791360034633)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05822140913284, 15.45779566717465),
                new GeoPoint( 47.05826871836141, 15.45791360034633)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05826871836141, 15.45791360034633),
                new GeoPoint( 47.05831406159309, 15.457873128416338)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05831406159309, 15.457873128416338),
                new GeoPoint( 47.05826863325425, 15.457756166515821)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05826863325425, 15.457756166515821),
                new GeoPoint( 47.058233098602805, 15.457786794316007)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.058233098602805, 15.457786794316007),
                new GeoPoint( 47.05823822607345, 15.457800447056854)
        ));

        //ITI Besprechungsraum
        walls.add(new Wall(
                new GeoPoint( 47.058211626849065, 15.457769551050745),
                new GeoPoint( 47.05818680078095, 15.457705773783488)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05818680078095, 15.457705773783488),
                new GeoPoint( 47.05823207062718, 15.457665809762318)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05823207062718, 15.457665809762318),
                new GeoPoint( 47.05825732345279, 15.457728875563163)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05825732345279, 15.457728875563163),
                new GeoPoint( 47.05822076605074, 15.457761301241078)
        ));

        //LAB South
        walls.add(new Wall(
                new GeoPoint( 47.058182898185585, 15.457778793317232),
                new GeoPoint( 47.05810986230869, 15.457594639242842)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05810986230869, 15.457594639242842),
                new GeoPoint( 47.05806618390153, 15.457633125110391)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05806618390153, 15.457633125110391),
                new GeoPoint( 47.05811185212362, 15.457751560786136)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05811185212362, 15.457751560786136),
                new GeoPoint( 47.05814599109367, 15.457722381396962)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05814599109367, 15.457722381396962),
                new GeoPoint( 47.05814097754344, 15.457709352327214)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05814097754344, 15.457709352327214),
                new GeoPoint( 47.058151141199254, 15.457734780489716)
        ));

        //Corridor
        walls.add(new Wall(
                new GeoPoint( 47.05815559128804, 15.457745155573946),
                new GeoPoint( 47.05817194600163, 15.457786933506753)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.0581766275821, 15.457799470678111),
                new GeoPoint( 47.05815433877437, 15.457820236701593)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05814330549331, 15.457830361145511),
                new GeoPoint( 47.058153204113516, 15.457855411697352)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.058153204113516, 15.457855411697352),
                new GeoPoint( 47.058187576129235, 15.457825934799587)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.058187576129235, 15.457825934799587),
                new GeoPoint( 47.05831266192748, 15.458143218423004)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05831266192748, 15.458143218423004),
                new GeoPoint( 47.05827836109888, 15.458174551610114)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05827836109888, 15.458174551610114),
                new GeoPoint( 47.058288686752064, 15.458199846018374)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.058288686752064, 15.458199846018374),
                new GeoPoint( 47.0583234239186, 15.458169744630538)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.0583234239186, 15.458169744630538),
                new GeoPoint( 47.0583368268193, 15.458204509276129)
        ));

        //Sozialraum
        walls.add(new Wall(
                new GeoPoint( 47.0583368268193, 15.458204509276129),
                new GeoPoint( 47.05832816443358, 15.458182739453889)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05832816443358, 15.458182739453889),
                new GeoPoint( 47.05829334353261, 15.45821272535801)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05829334353261, 15.45821272535801),
                new GeoPoint( 47.05830965928837, 15.458252296690915)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05830965928837, 15.458252296690915),
                new GeoPoint( 47.05834408037636, 15.458223205054196)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05834408037636, 15.458223205054196),
                new GeoPoint( 47.058341338874605, 15.458215687618804)
        ));

        walls.add(new Wall(
                new GeoPoint( 47.058341338874605, 15.458215687618804),
                new GeoPoint( 47.05834796417029, 15.45823245513273)
        ));

        //Sekretariat
        walls.add(new Wall(
                new GeoPoint( 47.058347566593525, 15.458233138457047),
                new GeoPoint( 47.05834359042932, 15.458223556263817)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05834359042932, 15.458223556263817),
                new GeoPoint( 47.058310505236214, 15.458252071947385)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.058310505236214, 15.458252071947385),
                new GeoPoint( 47.05833098030664, 15.458304190356046)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05833098030664, 15.458304190356046),
                new GeoPoint( 47.05836431017399, 15.458276123637347)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05836431017399, 15.458276123637347),
                new GeoPoint( 47.05835204087025, 15.458243541614706)
        ));

        walls.add(new Wall(
                new GeoPoint( 47.05835204087025, 15.458243541614706),
                new GeoPoint( 47.058395037497796, 15.458352419904116)
        ));

        //My Office
        walls.add(new Wall(
                new GeoPoint( 47.058395037497796, 15.458352419904116),
                new GeoPoint( 47.05839116664901, 15.458342336429723)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05839116664901, 15.458342336429723),
                new GeoPoint( 47.05835676514954, 15.4583712797359)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05835676514954, 15.4583712797359),
                new GeoPoint( 47.05838384833123, 15.458439204293114)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05838384833123, 15.458439204293114),
                new GeoPoint( 47.05841764032003, 15.45840987604069)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05841764032003, 15.45840987604069),
                new GeoPoint( 47.05839966003185, 15.458363085095101)
        ));

        //Corridor
        walls.add(new Wall(
                new GeoPoint( 47.05839966003185, 15.458363085095101),
                new GeoPoint( 47.05844871209297, 15.45848757969344)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05844871209297, 15.45848757969344),
                new GeoPoint( 47.05841433606323, 15.45851819809991)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05841433606323, 15.45851819809991),
                new GeoPoint( 47.05842456671125, 15.45854414788019)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05842456671125, 15.45854414788019),
                new GeoPoint( 47.05845939330664, 15.458514074146791)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05845939330664, 15.458514074146791),
                new GeoPoint( 47.05848022348873, 15.458568373911618)
        ));
        walls.add(new Wall(
                new GeoPoint( 47.05848022348873, 15.458568373911618),
                new GeoPoint( 47.05849147429609, 15.458558704400417)
        ));

        return walls;
    }

    public static ArrayList<Polyline> getITIWalls_poly(){
        ArrayList<Wall> walls = getITIWalls();
        ArrayList<Polyline> ret = new ArrayList<Polyline>();

        for(int i = 0; i < walls.size(); i++){
            Wall w = walls.get(i);
            Polyline nl = new Polyline();
            nl.addPoint(w.getVertices()[0]);
            nl.addPoint(w.getVertices()[1]);

            ret.add(nl);
        }

        return ret;
    }
}
