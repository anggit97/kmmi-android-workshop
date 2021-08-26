package com.anggit97.kmmiblog.ui.news;

import com.anggit97.kmmiblog.api.model.Post;

/**
 * Created by Anggit Prayogo on 26,August,2021
 * GitHub : https://github.com/anggit97
 */
public interface NewsAdapterActionListener {
    void onClickDelete(Post post, int absoluteAdapterPosition);
    void onClickEdit(Post post);
}
