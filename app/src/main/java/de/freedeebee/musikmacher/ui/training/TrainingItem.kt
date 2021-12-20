package de.freedeebee.musikmacher.ui.training

import de.freedeebee.musikmacher.data.model.TrainingSession
import de.freedeebee.musikmacher.util.convertLongToDateString
import de.freedeebee.musikmacher.util.durationToMinutes

class TrainingItem(
    session: TrainingSession
) {
    val date = convertLongToDateString(session.timeStarted!!)
    val duration = durationToMinutes(session.timeStarted!!, session.timeEnded!!)
}