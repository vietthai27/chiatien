package com.thai27.chiatien.ImageKit;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class ImageKitConfig {

    @Bean
    public ImageKit imageKit() {
        Configuration config = new Configuration(
                "public_p8vpXkH0q5X1bigCkCu6U3A2ihY=",
                "private_OQQciSfmyeepqKzjldIBfsU2WU0=",
                "https://ik.imagekit.io/c6ahlw7ck"
        );

        ImageKit imageKit = ImageKit.getInstance();
        imageKit.setConfig(config);

        return imageKit;
    }
}
