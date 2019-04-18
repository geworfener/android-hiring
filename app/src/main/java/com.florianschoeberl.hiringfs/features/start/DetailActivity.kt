package com.florianschoeberl.hiringfs.features.start

import android.graphics.drawable.Drawable
import android.os.Bundle
import com.florianschoeberl.hiringfs.R
import com.florianschoeberl.hiringfs.base.BaseActivity
import android.os.Build
import android.text.Spanned
import com.florianschoeberl.hiringfs.model.Club
import kotlinx.android.synthetic.main.detail_activity.*
import kotlinx.android.synthetic.main.main_activity.toolbar
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestOptions
import android.view.MenuItem
import android.transition.Fade
import androidx.core.text.HtmlCompat
import kotlinx.android.synthetic.main.detail_activity.image


const val TRANSITION_NAME = "TRANSITION_NAME"
const val CURRENT_ITEM = "CURRENT_ITEM"


class DetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportPostponeEnterTransition()

        val extras = intent.extras

        val club: Club? = extras?.getParcelable(CURRENT_ITEM)

        val imageView = image
        val detailView = detail
        val countryView = country

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val fade = Fade()
            fade.excludeTarget(R.id.appbar, true)
            fade.excludeTarget(R.id.toolbar, true)
            fade.excludeTarget(android.R.id.statusBarBackground, true)
            fade.excludeTarget(android.R.id.navigationBarBackground, true)
            window.enterTransition = fade
            window.exitTransition = fade

            val imageTransitionName = extras?.getString(TRANSITION_NAME)
            imageView.transitionName = imageTransitionName
        }

        title = club?.name

        val detailText: String = resources.getString(R.string.text_detail, club?.name, club?.country, club?.value)
        val styledDetailText: Spanned = HtmlCompat.fromHtml(detailText, HtmlCompat.FROM_HTML_MODE_LEGACY)

        detailView.text = styledDetailText
        countryView.text = club?.country

        val options = RequestOptions()
                .dontAnimate()
                .error(R.drawable.club_placeholder)

        Glide.with(this)
                .load(club?.image)
                .apply(options)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(imageView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
