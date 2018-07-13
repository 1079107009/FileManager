package com.lp.filemanager.ui.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.lp.filemanager.R
import com.lp.filemanager.utils.DensityUtil


/**
 * @author lipin
 * @date 18-7-11
 */
class DonutProgress : View {

    private lateinit var finishedPaint: Paint
    private lateinit var ringPaint: Paint
    private lateinit var innerCirclePaint: Paint
    private lateinit var textPaint: Paint

    private val rectF = RectF()

    private var textSize: Float = 0f
    private var textColor: Int = 0
    private var progress = 0
    private var max: Int = 0
        set(max) {
            if (max > 0) {
                field = max
            }
        }
    private var finishedStrokeColor: Int = 0
    private var ringColor: Int = 0
    private var strokeWidth: Float = 0f
    private var innerBackgroundColor: Int = 0
    private var suffixText: String = "%"

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.DonutProgress, defStyleAttr, 0)
        initByAttributes(attributes)
        attributes.recycle()
        initPainters()
    }

    private fun initByAttributes(attributes: TypedArray) {
        finishedStrokeColor = attributes.getColor(R.styleable.DonutProgress_donut_finished_color,
                ContextCompat.getColor(context, R.color.colorPrimaryDark))
        ringColor = attributes.getColor(R.styleable.DonutProgress_donut_ring_color, Color.GRAY)
        max = attributes.getInt(R.styleable.DonutProgress_donut_max, 100)
        strokeWidth = attributes.getDimension(R.styleable.DonutProgress_donut_stroke_width,
                DensityUtil.dp2px(context, 3f).toFloat())

        textColor = attributes.getColor(R.styleable.DonutProgress_donut_text_color, Color.BLACK)
        textSize = attributes.getDimension(R.styleable.DonutProgress_donut_text_size,
                DensityUtil.sp2px(context, 12f))

        innerBackgroundColor = attributes.getColor(R.styleable.DonutProgress_donut_background_color, Color.WHITE)
    }

    private fun initPainters() {
        textPaint = TextPaint()
        textPaint.color = textColor
        textPaint.textSize = textSize
        textPaint.isAntiAlias = true

        finishedPaint = Paint()
        finishedPaint.color = finishedStrokeColor
        finishedPaint.style = Paint.Style.STROKE
        finishedPaint.isAntiAlias = true
        finishedPaint.strokeWidth = strokeWidth

        ringPaint = Paint()
        ringPaint.color = ringColor
        ringPaint.style = Paint.Style.STROKE
        ringPaint.isAntiAlias = true

        innerCirclePaint = Paint()
        innerCirclePaint.color = innerBackgroundColor
        innerCirclePaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec))
    }

    private fun measure(measureSpec: Int): Int {
        val mode = View.MeasureSpec.getMode(measureSpec)
        val size = View.MeasureSpec.getSize(measureSpec)
        return if (mode == View.MeasureSpec.EXACTLY) size else 0
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制背景
        val innerCircleRadius = width / 2f
        val cx = width / 2.0f
        val cy = height / 2.0f
        canvas.drawCircle(cx, cy, innerCircleRadius, innerCirclePaint)

        // 绘制外环
        ringPaint.strokeWidth = 2f
        canvas.drawCircle(cx, cy, innerCircleRadius - 2, ringPaint)

        // 绘制内环
        ringPaint.strokeWidth = 1f
        canvas.drawCircle(cx, cy, innerCircleRadius - strokeWidth - 1, ringPaint)

        // 绘制进度
        val progressAngle = progress / this.max.toFloat() * 360f
        rectF.set(strokeWidth - 2, strokeWidth - 2, width.toFloat() - strokeWidth + 2, height.toFloat() - strokeWidth + 2)
        canvas.drawArc(rectF, 270f, progressAngle, false, finishedPaint)

        // 绘制文字
        val text = this.progress.toString() + suffixText
        val textHeight = textPaint.descent() + textPaint.ascent()
        canvas.drawText(text, (width - textPaint.measureText(text)) / 2.0f, (width - textHeight) / 2.0f, textPaint)
    }

    fun setProgress(progress: Int) {
        if (progress <= 0 || progress > max) {
            return
        }
        val animator = ValueAnimator.ofInt(0, progress)
        animator.duration = 2000
        animator.start()
        animator.addUpdateListener {
            (it.animatedValue as Int?)?.let {
                this.progress = it
                invalidate()
            }
        }
    }

}
