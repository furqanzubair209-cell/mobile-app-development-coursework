package com.example.lists;

import java.util.ArrayList;
import java.util.List;

public class GadgetProvider {
    public static List<Gadget> getGadgets() {
        List<Gadget> list = new ArrayList<>();

        list.add(new Gadget(
                "Apple iPhone 16 Pro Max",
                "Grade 5 titanium chassis powered by the A18 Pro chip with 6.9-inch 120Hz ProMotion. Features a 48MP Fusion camera with 5x optical telephoto and 4K 120 fps Dolby Vision.",
                "Flagship",
                4.9,
                2450,
                R.drawable.ic_logo_apple
        ));

        list.add(new Gadget(
                "Samsung Galaxy S24 Ultra",
                "Titanium frame with integrated S Pen and 6.8-inch Dynamic AMOLED 2X. Driven by Snapdragon 8 Gen 3 with Galaxy AI, 200MP Quad Telephoto, and 100x Space Zoom.",
                "AI Flagship",
                4.9,
                1980,
                R.drawable.ic_logo_samsung
        ));

        list.add(new Gadget(
                "Google Pixel 9 Pro XL",
                "Powered by Google Tensor G4 with 16GB RAM for advanced on-device Gemini AI. Features a 6.8-inch 3000-nit Actua OLED and pro triple camera array with Magic Editor.",
                "Pure Android",
                4.8,
                1620,
                R.drawable.ic_logo_google
        ));

        list.add(new Gadget(
                "Sony Xperia 1 VI",
                "Pro Alpha camera tech with Zeiss optics and continuous 85-170mm optical zoom. Equipped with Snapdragon 8 Gen 3, a 120Hz OLED, and a dedicated 3.5mm Hi-Res headphone jack.",
                "Pro Camera",
                4.7,
                890,
                R.drawable.ic_logo_sony
        ));

        list.add(new Gadget(
                "Xiaomi 14 Ultra",
                "Co-engineered with Leica featuring quad 50MP cameras with stepless variable aperture. Offers a 6.73-inch WQHD+ AMOLED, Snapdragon 8 Gen 3, and 90W HyperCharge.",
                "Camera Beast",
                4.8,
                1420,
                R.drawable.ic_logo_xiaomi
        ));

        list.add(new Gadget(
                "OnePlus 12",
                "Pairs Snapdragon 8 Gen 3 with Dual Vapor Chamber cooling and a 4500-nit 2K ProXDR screen. Features Hasselblad cameras and ultra-fast 100W SUPERVOOC charging.",
                "Performance",
                4.8,
                1750,
                R.drawable.ic_logo_oneplus
        ));

        list.add(new Gadget(
                "Motorola Edge 50 Ultra",
                "Authentic real wood and vegan leather finishes with IP68 underwater protection. Packs a 6.7-inch 144Hz pOLED screen, Snapdragon 8s Gen 3, and 125W TurboPower.",
                "Style Icon",
                4.6,
                940,
                R.drawable.ic_logo_motorola
        ));

        list.add(new Gadget(
                "Asus ROG Phone 8 Pro",
                "Hardcore gaming powerhouse featuring Snapdragon 8 Gen 3, 165Hz AMOLED, and rear AniMe Vision matrix. Includes AirTrigger ultrasonic controls and GameCool 8 thermals.",
                "Gaming Beast",
                4.9,
                1120,
                R.drawable.ic_logo_asus
        ));

        list.add(new Gadget(
                "Nothing Phone (2a) Plus",
                "Transparent back aesthetics with the iconic customizable Glyph Interface lights. Powered by Dimensity 7350 Pro with a 120Hz flexible AMOLED and dual 50MP cameras.",
                "Unique Design",
                4.6,
                1340,
                R.drawable.ic_logo_nothing
        ));

        list.add(new Gadget(
                "Vivo X100 Pro",
                "Co-developed with ZEISS featuring an APO telephoto lens and 1-inch Sony IMX989 sensor. Driven by Dimensity 9300 with dedicated V3 imaging chip and 120Hz LTPO display.",
                "ZEISS Optics",
                4.8,
                1080,
                R.drawable.ic_logo_vivo
        ));

        list.add(new Gadget(
                "Oppo Find X7 Ultra",
                "World's first phone with Dual Periscope Telephoto lenses tuned by Hasselblad. Features a 1-inch Sony LYT-900 sensor, Snapdragon 8 Gen 3, and 100W SuperVOOC flash charging.",
                "Dual Periscope",
                4.8,
                1210,
                R.drawable.ic_logo_oppo
        ));

        list.add(new Gadget(
                "Realme GT 6",
                "Flagship killer driven by Snapdragon 8s Gen 3 with an ultra-bright 6000-nit 8T LTPO 120Hz display. Packs a 50MP Sony LYT-808 camera, 5500mAh battery, and 120W charging.",
                "Flagship Killer",
                4.7,
                1560,
                R.drawable.ic_logo_realme
        ));

        list.add(new Gadget(
                "Honor Magic6 Pro",
                "Falcon Camera System with 180MP periscope telephoto and self-adjusting variable aperture. Protected by NanoCrystal Shield glass with a massive 5600mAh silicon-carbon battery.",
                "Falcon Camera",
                4.8,
                980,
                R.drawable.ic_logo_honor
        ));

        list.add(new Gadget(
                "Huawei Mate 60 Pro",
                "Pioneering satellite calling and ultra-durable Kunlun Glass 2 protection. Driven by Kirin 9000S and XMAGE triple camera with adjustable physical aperture and HarmonyOS.",
                "Satellite Tech",
                4.7,
                1890,
                R.drawable.ic_logo_huawei
        ));

        list.add(new Gadget(
                "RedMagic 9 Pro",
                "Stealth gaming flagship with flat back glass and built-in 22,000 RPM RGB cooling fan. Powered by Snapdragon 8 Gen 3, under-display front camera, and 6500mAh battery.",
                "Active Cooling",
                4.8,
                870,
                R.drawable.ic_logo_nubia
        ));

        list.add(new Gadget(
                "Razer Edge 5G",
                "Handheld Android gaming console powered by Snapdragon G3x Gen 1 with active cooling. Features a 144Hz AMOLED screen and bundled Kishi V2 Pro haptic controller.",
                "Handheld Console",
                4.5,
                620,
                R.drawable.ic_logo_razer
        ));

        list.add(new Gadget(
                "Microsoft Surface Duo 2",
                "Dual 5.8-inch AMOLED displays opening into an 8.3-inch workspace connected by a 360-degree hinge. Features Snapdragon 888 5G, Glance Bar spine, and Surface Pen support.",
                "Dual Screen",
                4.4,
                510,
                R.drawable.ic_logo_microsoft
        ));

        list.add(new Gadget(
                "LG Wing 5G",
                "Unique swivel form factor rotating 90 degrees horizontally to reveal a secondary 3.9-inch display. Includes Gimbal Motion Camera controls with physical joystick on lower screen.",
                "Swivel Screen",
                4.3,
                780,
                R.drawable.ic_logo_lg
        ));

        list.add(new Gadget(
                "Nokia XR21",
                "Engineered to MIL-STD-810H military durability with IP69K waterproof protection. Features a 120Hz Victus-shielded display, 64MP AI camera, and 2-day battery life.",
                "Rugged Hero",
                4.5,
                430,
                R.drawable.ic_logo_nokia
        ));

        list.add(new Gadget(
                "HTC U24 Pro",
                "Curved 6.7-inch 120Hz OLED with IP67 water resistance and integrated VIVERSE VR ecosystem. Powered by Snapdragon 7 Gen 3 with 50MP OIS camera and wireless charging.",
                "VIVERSE VR",
                4.4,
                390,
                R.drawable.ic_logo_htc
        ));

        return list;
    }
}
