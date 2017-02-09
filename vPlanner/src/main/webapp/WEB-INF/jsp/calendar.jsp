<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My calendar</title>

<link rel='stylesheet'
	href='resources/css/fullcalendar/fullcalendar.css' />
<link rel='stylesheet' href='resources/css/bootstrap/bootstrap.min.css' />
<script src='resources/js/fullcalendar/jquery.min.js'></script>
<script src='resources/js/bootstrap/bootstrap.min.js'></script>
<script src='resources/js/fullcalendar/jquery-ui.min.js'></script>
<script src='resources/js/fullcalendar/moment.min.js'></script>
<script src='resources/js/fullcalendar/fullcalendar.js'></script>
<script src='resources/js/fullcalendar/gcal.js'></script>
<script>
	$(document)
			.ready(
					function() {

						// page is now ready, initialize the calendar...

						$('#calendar')
								.fullCalendar(
										{
											header : {
												left : 'title',
												center : false,
												right : 'today'
											},

											events : "/vacationPlan",
											editable : false,
											/*
											selectable:true will enable user to select datetime slot
											selectHelper will add helpers for selectable.
											 */
											selectable : true,
											selectHelper : true,
											/* dayClick : function() {
												alert('a day has been clicked!');
											},
											 */
											select : function(start, end,
													allDay) {
												/*
												after selection user will be promted for enter title for event.
												 */
												/* endTime = $.fullCalendar.formatDate(end, 'yyyy/MM/dd');
												startTime = $.fullCalendar.formatDate(start, 'yyyy/MM/dd');
												 */
												var mywhen = start + ' - '
														+ end;

												$(
														'#createVacationPlanModal #start')
														.val(start);
												$(
														'#createVacationPlanModal #end')
														.val(end);
												$(
														'#createVacationPlanModal #allDay')
														.val(allDay);
												$(
														'#createVacationPlanModal #when')
														.text(mywhen);
												$('#createVacationPlanModal')
														.modal('show');
											
												$('#calendar').fullCalendar(
														'unselect');
											},
											/*
											editable: true allow user to edit events.
											 */
											editable : true,

										// put your options and callbacks here
										});

						/* Save vacation plan on the client */
						$('#saveVacationPlan').on('click', function(e) {
							e.preventDefault();
							saveVacationPlan();
						});

						function saveVacationPlan() {
							$('#createVacationPlanModal').modal('hide');

							$('#calendar').fullCalendar('renderEvent', {
								title : $('#title').val(),
								description : $('#description').val(),
								start : new Date($('#start').val()),
								end : new Date($('#end').val()),
								allDay : ($('#allDay').val() == "true"),
							}, true);
						}

						/*Save or update all vacations plans on the server*/
						$('#saveFullVacationPlan').on('click', function(e) {
							e.preventDefault();
							saveFullVacationPlan();
						});

						function saveFullVacationPlan() {	
							var vacationsPlansFromCalendar = $('#calendar').fullCalendar('clientEvents');
							
							var vacationsPlansToJSON = $.map(vacationsPlansFromCalendar, function (item, i) {
								return {
				                id : item.id,
				                title : item.title,
				                description : item.description,
				                start : item.start,
				                end : item.end
								};
				            });
							 var vacationsPlans = JSON.stringify(vacationsPlansToJSON)
					 		$.ajax({
								type : "POST",
								url : "/vacationPlan/saveFullVacationPlan",
								data : vacationsPlans,
								dataType: 'json',
								contentType: "application/json; charset=utf-8",
								success: function (msg) {
						               //do something
						        },
						        error: function (errormessage) {
						                //do something else
						        }
							}); 
						}	

						function goToMonth(month) {
							var date = new Date();
							var d = date.getDate();
							var m = month !== undefined ? parseInt(month, 0)
									: date.getMonth();
							var y = date.getFullYear();
							return new Date(y, m, d);
						}

						$(function() {
							var date = new Date();
							var d = date.getDate();
							var m = date.getMonth();
							var y = date.getFullYear();

							calendarNavigation('#calendarNavigation-list');

							$(document)
									.on(
											'click',
											'#calendarNavigation-list a',
											function() {
												// get index from calendarNavigation anchor hash
												var index = this.hash
														.substring(1);
												// go to new month
												if (index < 12) {
													date.setMonth(index);
													$('#calendar')
															.fullCalendar(
																	'gotoDate',
																	date);
												} else {
													date.setFullYear(index);
													calendarNavigation('#calendarNavigation-list');
													$('#calendar')
															.fullCalendar(
																	'gotoDate',
																	date);
												}
												return false;
											});

							function calendarNavigation(element) {
								$(element).empty();
								var monthNames = [ "Jan", "Feb", "Mar", "Apr",
										"May", "Jun", "Jul", "Aug", "Sep",
										"Oct", "Nov", "Dec" ];

								var prevYear = (date.getFullYear() - 1);
								var nextYear = (date.getFullYear() + 1);
								$(element).append(
										'<li id="year"><a href="#' + prevYear +'">'
												+ prevYear + '</a></li>');
								for (var m = 0; m < monthNames.length; m++) {
									$(element).append(
											'<li><a href="#' + m + '">'
													+ monthNames[m]
													+ '</a></li>');
								}
								$(element).append(
										'<li id="year"><a href="#' + nextYear +'">'
												+ nextYear + '</a></li>');
							}

						});

					});
</script>

<style>
ul.calendarNavigation {
	margin: 0 auto;
	text-align: center;
}

ul.calendarNavigation li {
	display: inline;
	margin-right: 5px;
}

ul.calendarNavigation li.year {
	margin-right: 15px;
}

.fc-sun, .fc-sat {
	background-color: grey;
}
</style>

</head>
<body>
	<h1>My calendar</h1>
	<ul class="calendarNavigation" id="calendarNavigation-list"></ul>
	<div id='calendar'></div>

	<button type="submit" class="btn btn-primary" id="saveFullVacationPlan">Save</button>


	<div id="createVacationPlanModal" class="modal hide" tabindex="-1"
		role="dialog" aria-labelledby="createVacationPlanLebel"
		aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">x</button>
			<h3 id="createVacationPlanLebel">Vacation Plan</h3>
		</div>
		<div class="modal-body">
			<form id="createVacationPlanForm" class="form-horizontal">

				<div class="control-group">
					<label class="control-label" for="when">When:</label>
					<div class="controls controls-row" id="when"
						style="margin-top: 5px;"></div>
				</div>

				<div class="control-group">
					<label class="control-label" for="inputPatient">Title:</label>
					<div class="controls">
						<input type="text" name="title" id="title"> <input
							type="hidden" id="start" /> <input type="hidden" id="end" /> <input
							type="hidden" id="allDay" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="inputPatient">Description:</label>
					<div class="controls">
						<input type="text" name="description" id="description">
					</div>
				</div>

			</form>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
			<button type="submit" class="btn btn-primary" id="saveVacationPlan">Save</button>
		</div>
	</div>



</body>
</html>